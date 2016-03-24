package com.oaganalytics.apib

import argonaut._, Argonaut._
import scalaz._, Scalaz._

case class Document(content: List[Category])

case class Meta(description: Option[String], classes: Option[List[String]], id: Option[String], title: Option[String])

case class Member(content: KeyValue, meta: Option[Meta], attributes: Option[MemberAttributes]) {
  val key = content.key
  val keyString = content.keyString
  val value = content.value
}
case class KeyValue(key: StringElement, value: Element) {
  val keyString = key.content
}

sealed trait Element
case class StringElement(content: Option[String], attributes: Option[ElementAttributes]) extends Element
case class ArrayElement(content: Element, attributes: Option[ArrayAttributes]) extends Element
case class Reference(element: String) extends Element { def content = element }
case class NumberElement(content: Option[Double], attributes: Option[ElementAttributes]) extends Element
case class BooleanElement(content: Option[Boolean], attributes: Option[ElementAttributes]) extends Element
case class EnumElement(content: List[StringElement], attributes: Option[EnumAttributes]) extends Element
case class LiteralObject() extends Element
case class DummyObject() extends Element
case class DummyArrayObject() extends Element

case class MemberAttributes(typeAttributes: List[String])
case class ArrayAttributes(default: List[StringElement])
case class ElementAttributes(default: String)
case class EnumAttributes(default: Option[List[StringElement]], samples: List[List[StringElement]])
case class ObjectAttributes(samples: List[List[StringElement]])

case class HttpHeaders(content: List[Member]) {
  val keyStrings = content.map(_.keyString)
  // TODO somehow typecheck this into Map[String, String] via better decoding
  val toMap = content.map(mem => (mem.keyString.get, mem.value.asInstanceOf[StringElement].content.get)).toMap
}

sealed trait HttpDescriptionContent
case class Asset(meta: Option[Meta], attributes: Option[AssetAttributes], content: String) extends HttpDescriptionContent
case class AssetAttributes(contentType: String)

case class HttpDescription(dataStructure: Option[DataStructure], assets: List[Asset])

case class HttpResponse(attributes: HttpResponseAttributes, content: HttpDescription) {
  val statusCode = attributes.statusCode
  val headers = attributes.headers
  val dataStructure = content.dataStructure
}
case class HttpResponseAttributes(statusCode: Int, headers: HttpHeaders)

case class HttpRequest(attributes: HttpRequestAttributes, content: HttpDescription) {
  val method = attributes.method
  val title = attributes.title
  val headers = attributes.headers
  val dataStructure = content.dataStructure
}
case class HttpRequestAttributes(method: String, title: Option[String], headers: Option[HttpHeaders])

case class HttpTransaction(request: HttpRequest, response: HttpResponse)

case class StructObject(content: List[Member], meta: Option[Meta]) extends Element
case class DataStructure(content: Element) extends HttpDescriptionContent with ResourceContent

case class Resource(attributes: ResourceAttributes, meta: ResourceMeta, content: List[ResourceContent]) extends ResourceContent with CategoryContent{
  def href = attributes.href
}
case class ResourceAttributes(href: String)
case class ResourceMeta(title: String, description: Option[String])

sealed trait ResourceContent
case class Transition(content: HttpTransaction, meta: Meta, attributes: Option[TransitionAttributes]) extends ResourceContent {
  val request = content.request
  val response = content.response
}
case class Copy(content: String) extends ResourceContent with CategoryContent

case class TransitionAttributes(href: String, hrefVariables: HrefVariables)

case class HrefVariables(content: List[Member])

case class Category(content: List[CategoryContent], title: String, classes: List[String]) extends ResourceContent with CategoryContent
sealed trait CategoryContent

object Decoder {
  def validateElement(name: String): HCursor => String \/ Unit = { c =>
    (c --\ "element").as[String].toDisjunction.fold(
      _ => -\/("Found no element"),
      { found =>
        if(found == name) {
          \/-(())
        } else {
          -\/(s"Expected $name, found $found")
        }
      }
    )
  }
  def decodeAndValidate[T](name: String)(implicit decode: DecodeJson[T]): DecodeJson[T] =
    DecodeJson({c =>
      validateElement(name)(c) match {
        case -\/(message) => DecodeResult.fail[T](message, c.history)
        case \/-(()) => decode(c)
      }
    })

  def kindDecode[T](
    kinds: Map[String, Function0[DecodeJson[T]]],
    fail: Option[HCursor => DecodeResult[T]] = None
  ): DecodeJson[T] = DecodeJson(c =>
    (c --\ "element").as[String].flatMap { kind =>
      kinds.get(kind).map(_.apply.decode(c)).getOrElse(
        fail.getOrElse(
          { c: HCursor => DecodeResult.fail[T](s"expected one of ${kinds.keys}, got $kind", c.history) }
        )(c))
    }
  )

  def find[T: DecodeJson]: DecodeJson[T] = DecodeJson({_.downField("content").downArray.find(j => ! j.as[T].isError).as[T]})

  def dummyDecode[T](key: String, value: T): DecodeJson[T] = decodeAndValidate[T](key)(DecodeJson(c =>
    c.fieldSet.map(set =>
      if (set == Set("element") && (c --\ "element").as[String] == DecodeResult.ok(key)) {
        DecodeResult.ok(value)
      } else {
        DecodeResult.fail[T](s"Not a dummy element, got additional field names: $set.", c.history)
      })
      .getOrElse(DecodeResult.fail[T]("No fields", c.history))
  ))

  implicit def elementDecode: DecodeJson[Element] = kindDecode(
    Map(
      "string" -> { () => stringElementDecode.map(identity[Element])},
      "number" -> { () => numberElementDecode.map(identity[Element])},
      "boolean" -> { () => booleanElementDecode.map(identity[Element])},
      "array" -> { () => arrayElementDecode.map(identity[Element])},
      "enum" -> { () => enumElementDecode.map(identity[Element])},
      "object" -> { () => structObjectDecode.map(identity[Element]) ||| literalObjectDecode.map(identity[Element]) ||| dummyObjectDecode.map(identity[Element])},
      "[]" -> { () => dummyArrayObjectDecode.map(identity[Element])}
    ),
    Some({c => DecodeJson.of[Reference].decode(c).map(identity[Element])})
  )

  implicit def descriptionsDecode: DecodeJson[HttpDescriptionContent] = kindDecode(
    Map(
      "asset" -> { () => assetDecode.map(identity[HttpDescriptionContent])},
      "dataStructure" -> { () => dataStructureDecode.map(identity[HttpDescriptionContent])}
    )
  )

  implicit def resourceContentDecode: DecodeJson[ResourceContent] = kindDecode(Map(
    "dataStructure" -> {() => dataStructureDecode.map(identity[ResourceContent])},
    "transition" -> {() => transitionDecode.map(identity[ResourceContent])},
    "copy" -> {() => copyDecode.map(identity[ResourceContent])},
    "category" -> {() => categoryDecode.map(identity[ResourceContent])},
    "resource" -> {() => resourceDecode.map(identity[ResourceContent])}
  ))

  implicit def categoryContentDecode: DecodeJson[CategoryContent] = kindDecode(Map(
    "category" -> {() => categoryDecode.map(identity[CategoryContent])},
    "resource" -> {() => resourceDecode.map(identity[CategoryContent])},
    "copy" -> {() => copyDecode.map(identity[CategoryContent])}
  ))

  implicit def keyValueDecode: DecodeJson[KeyValue] = jdecode2L(KeyValue.apply)("key", "value")
  implicit def requestAttributesDecode: DecodeJson[HttpRequestAttributes] = jdecode3L(HttpRequestAttributes.apply)("method", "title", "headers")
  implicit def elementAttributesDecode: DecodeJson[ElementAttributes]= jdecode1L(ElementAttributes.apply)("default")
  implicit def arrayElementDecode: DecodeJson[ArrayElement] = DecodeJson({c =>
    (c --\ "attributes").as[Option[ArrayAttributes]].flatMap({attributes =>
      (c --\ "content").as[List[Element]].flatMap({list =>
        list match {
          case List(dummy: DummyObject, el: Element) => DecodeResult.ok(ArrayElement(el, attributes))
          case List(el: Element) => DecodeResult.ok(ArrayElement(el, attributes))
          case _ => DecodeResult.fail("Array are supposed to have one element only. For more than one element, wrap it in an object.", c.history)
        }
      })
    })
  })
  implicit def referenceDecode: DecodeJson[Reference] = jdecode1L(Reference.apply)("element")
  implicit def arrayAttributesDecode: DecodeJson[ArrayAttributes] = jdecode1L(ArrayAttributes.apply)("default")
  implicit def memberAttributesDecode: DecodeJson[MemberAttributes] = jdecode1L(MemberAttributes.apply)("typeAttributes")
  implicit def enumAttributesDecode: DecodeJson[EnumAttributes] = jdecode2L(EnumAttributes.apply)("default", "samples")
  implicit def objectAttributesDecode: DecodeJson[ObjectAttributes] = jdecode1L(ObjectAttributes.apply)("samples")

  implicit def stringElementDecode: DecodeJson[StringElement] = decodeAndValidate[StringElement]("string")(jdecode2L(StringElement.apply)("content", "attributes"))
  implicit def numberElementDecode: DecodeJson[NumberElement] = decodeAndValidate[NumberElement]("number")(jdecode2L(NumberElement.apply)("content", "attributes"))
  implicit def booleanElementDecode: DecodeJson[BooleanElement] = decodeAndValidate[BooleanElement]("boolean")(jdecode2L(BooleanElement.apply)("content", "attributes"))
  implicit def enumElementDecode: DecodeJson[EnumElement] = decodeAndValidate[EnumElement]("enum")(jdecode2L(EnumElement.apply)("content", "attributes"))
  implicit def literalObjectDecode: DecodeJson[LiteralObject] = decodeAndValidate[LiteralObject]("object")(DecodeJson(c =>
    (c --\ "content").as[List[StringElement]].flatMap({ se =>
      if(se.length == 1 && se(0).content.map(s => s.startsWith("{") && s.endsWith("}")).getOrElse(false)) {
        DecodeResult.ok(LiteralObject())
      } else {
        DecodeResult.fail(s"The content wasn't a single {}, not a literal object", c.history)
      }
    })
  ))
  implicit def dummyObjectDecode: DecodeJson[DummyObject] = dummyDecode("object", DummyObject())
  implicit def dummyArrayObjectDecode: DecodeJson[DummyArrayObject] = dummyDecode("[]", DummyArrayObject())

  implicit def documentDecode: DecodeJson[Document] = decodeAndValidate[Document]("parseResult")(jdecode1L(Document.apply)("content"))
  implicit def memberDecode: DecodeJson[Member] = decodeAndValidate[Member]("member")(jdecode3L(Member.apply)("content", "meta", "attributes"))
  implicit def headersDecode: DecodeJson[HttpHeaders] = decodeAndValidate[HttpHeaders]("httpHeaders")(jdecode1L(HttpHeaders.apply)("content"))
  implicit def assetDecode: DecodeJson[Asset] = decodeAndValidate[Asset]("asset")(jdecode3L(Asset.apply)("meta", "attributes", "content"))
  implicit def assetAttributesDecode: DecodeJson[AssetAttributes] = jdecode1L(AssetAttributes.apply)("contentType")
  implicit def responseDecode: DecodeJson[HttpResponse] = decodeAndValidate[HttpResponse]("httpResponse")(jdecode2L(HttpResponse.apply)("attributes", "content"))
  implicit def responseAttributesDecode: DecodeJson[HttpResponseAttributes] = jdecode2L(HttpResponseAttributes.apply)("statusCode", "headers")
  implicit def requestDecode: DecodeJson[HttpRequest] = decodeAndValidate[HttpRequest]("httpRequest")(jdecode2L(HttpRequest.apply)("attributes", "content"))
  implicit def transactionDecode: DecodeJson[HttpTransaction] = decodeAndValidate[HttpTransaction]("httpTransaction")(DecodeJson({c =>
    for {
      req <- find[HttpRequest].apply(c)
      resp <- find[HttpResponse].apply(c)
    } yield HttpTransaction(req, resp)
  }))
  implicit def httpDescriptionDecode: DecodeJson[HttpDescription] = implicitly[DecodeJson[List[HttpDescriptionContent]]].flatMap({content =>
    DecodeJson({c =>
    val (dataStructures, assets) = content.foldLeft((List[DataStructure](), List[Asset]()))({case (keep, elem) =>
      elem match {
        case a: Asset => (keep._1, a :: keep._2)
        case ds: DataStructure => (ds :: keep._1, keep._2)
      }
    })
    dataStructures.length match {
      case 0 => DecodeResult.ok(HttpDescription(None, assets))
      case 1 => DecodeResult.ok(HttpDescription(dataStructures.headOption, assets))
      case _ => DecodeResult.fail("Found more than one dataStructure inside a single request/response.", c.history)
    }
  })})
  implicit def structObjectDecode: DecodeJson[StructObject] = decodeAndValidate[StructObject]("object")(jdecode2L(StructObject.apply)("content", "meta"))
  implicit def metaDecode: DecodeJson[Meta] = jdecode4L(Meta.apply)("description", "classes", "id", "title")
  implicit def dataStructureDecode: DecodeJson[DataStructure] = decodeAndValidate[DataStructure]("dataStructure")(find[Element].map(x => DataStructure.apply(x)))
  implicit def resourceDecode: DecodeJson[Resource] = decodeAndValidate[Resource]("resource")(jdecode3L(Resource.apply)("attributes", "meta", "content"))
  implicit def resourceAttributesDecode: DecodeJson[ResourceAttributes] = jdecode1L(ResourceAttributes)("href")
  implicit def transitionDecode: DecodeJson[Transition] = decodeAndValidate("transition")(DecodeJson(c =>
    for {
      meta <- (c --\ "meta").as[Meta]
      attributes <- (c --\ "attributes").as[Option[TransitionAttributes]]
      content <- find[HttpTransaction].apply(c)
    } yield Transition(content, meta, attributes)
  ))
  implicit def resourceMetaDecode: DecodeJson[ResourceMeta] = jdecode2L(ResourceMeta.apply)("title", "description")
  implicit def transitionAttributesDecode: DecodeJson[TransitionAttributes] = jdecode2L(TransitionAttributes.apply)("href", "hrefVariables")
  implicit def hrefVariablesDecode: DecodeJson[HrefVariables] = decodeAndValidate("hrefVariables")(jdecode1L(HrefVariables.apply)("content"))
  implicit def copyDecode: DecodeJson[Copy] = jdecode1L(Copy.apply)("content")
  implicit def categoryDecode: DecodeJson[Category] = decodeAndValidate("category")(DecodeJson(c =>
    for {
      content <- (c --\ "content").as[List[CategoryContent]]
      title <- (c --\ "meta" --\ "title").as[String]
      classes <- (c --\ "meta" --\ "classes").as[List[String]]
    } yield Category(content, title, classes)
  ))
}
