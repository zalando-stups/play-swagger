package com.oaganalytics.apib
import de.zalando.apifirst._
import Domain._, Application._
import scalaz._, Scalaz._

case class Response(statusCode: Int, ref: naming.Reference, parameter: Parameter, headers: Map[String, String])
// More parameters are in the transition
case class Request(verb: Http.Verb, parameter: Option[Parameter], headers: Map[String, String])
case class Href(path: naming.Reference, query: Set[String]) {
  def compile(vars: List[(ParameterRef, Type)]): ParameterLookupTable = {
    vars.map({ case (ref, typ) =>
      val name = ref.name.parts.last
      val place = if (query(name)) { ParameterPlace.QUERY } else { ParameterPlace.PATH }
      ref -> Parameter(
        name = name,
        typeName = typ,
        fixed = None,
        default = None,
        constraint = """[^/]+""",
        encode = false,
        place = place
      )
    }).toMap
  }
}
object Href {
  def apply(s: String): Href = {
    val parts = s.split("/").filter(_ != "").toList
    val (lastDefault, remainder) = parts.lastOption.map({ last =>
      val m = "\\{\\W".r.pattern.matcher(last)
      if (m.find) {
        val index = m.end - 2
        (Some(last.slice(0, index)), if(m.end == last.length) None else Some(last.slice(index, last.length)))
      } else {
        (Some(last), None)
      }
    }).getOrElse((None, None))
    val queries = remainder.map(rem => """\{\?\w+?\}""".r.findAllIn(rem).toList.map({s => s.slice(2, s.length - 1)})).getOrElse(List())
    // TODO support everything https://apiblueprint.org/documentation/specification.html#uri-template-variable
    val defaults: List[String] = (if(parts.isEmpty) List.empty else parts.init  ++ lastDefault)
    Href(naming.Reference(if(defaults.empty) List("/") else defaults), queries.toSet)
  }
}
case class ResourceTranslation(calls: List[ApiCall], parameters: ParameterLookupTable, types: TypeLookupTable)
object ResourceTranslation {
  implicit def resourceTranslationMonoid = new Monoid[ResourceTranslation] {
    def zero = ResourceTranslation(List(), Map(), Map())
    def append(f1: ResourceTranslation, f2: => ResourceTranslation): ResourceTranslation = {
      ResourceTranslation(f1.calls ++ f2.calls, f1.parameters ++ f2.parameters, f1.types ++ f2.types)
    }
  }
}
import ResourceTranslation._

object Generator {
  val emptyMeta = TypeMeta(None)
  val pureObjectName = naming.Reference(List("definitions", "pureJSObject"))
  val pureObjectDef = ResourceTranslation(List(), Map(), Map(pureObjectName -> CatchAll(Str(None, None), emptyMeta)))
  val pureObject = TypeRef(pureObjectName)
  val escapees = List(
    " " -> "",
    "&" -> "_ampersand_",
    "/" -> "_slash_",
    "\\\\" -> "_backslash_",
    "'" -> "_singlequote_",
    "\"" -> "_doublequote_",
    "\\(" -> "_openparens_",
    "\\)" -> "_closeparens_",
    "\\{" -> "_openbrace_",
    "\\}" -> "_closebrace_"
  )
  /*
   Replace all characters with are invalid in Scala type/variable names.
   */
  def escape(name: String): String = escapees.foldLeft(name)({case (name, (literal, escaped)) => name.replaceAll(literal, escaped) })

  def transform(element: Element, meta: TypeMeta, path: naming.TypeName): Type = {
    element match {
      case StringElement(content, attributes) => Str(None, meta)
      case ArrayElement(content, attributes) => ArrResult(transform(content, emptyMeta, path / "array"), meta)
      case Reference(to) => TypeRef(naming.Reference.definition(escape(to)))
      case NumberElement(content, attributes) => Dbl(meta)
      case BooleanElement(content, attributes) => Bool(meta)
      case EnumElement(content, attributes) => {
        val elements: List[String] = content.flatMap(_.content)
        Str(None, meta)
      }
      case LiteralObject() => pureObject
      case DummyObject() => pureObject
      case DummyArrayObject() => ArrResult(pureObject, meta)
      case so: StructObject => {
        transform(so, meta, path)
      }
    }
  }

  def transform(so: StructObject, meta: TypeMeta, path: naming.TypeName): TypeDef = {
    val p = so.meta.flatMap(_.id).map(id => naming.Reference.definition(escape(id))).getOrElse(path)
    TypeDef(
      p,
      so.content
        .map({ member =>
          val name = member.keyString.getOrElse(throw new IllegalStateException(s"An object member needs a key name. Illegal object: $so"))
          transform(member, p, escape(name))
        }),
      meta
    )
  }

  def transform(member: Member, path: naming.TypeName, fieldname: String): Field = {
    val meta = TypeMeta(member.meta.flatMap(_.description))
    // the default for an attribute is required
    val optional = member.attributes.flatMap(_.typeAttributes.find(_ == "optional").map(x => true)).getOrElse(false)
    val elem = if (optional) {
      val elem = transform(member.value, emptyMeta, path / fieldname)
      Opt(elem, meta)
    } else {
      transform(member.value, meta, path / fieldname)
    }
    Field(path / fieldname, elem)
  }

  def transform(datastructure: DataStructure, path: naming.TypeName): Type = {
    transform(datastructure.content, emptyMeta, path)
  }

  def transform(request: HttpRequest, ref: naming.Reference): Request = {
    val verb = Http.string2verb(request.method).getOrElse { throw new IllegalStateException(s"Invalid verb: ${request.method}") }
    Request(
      verb,
      request.dataStructure.map(ds =>
        Parameter(
          name = "requestBody",
          typeName = transform(ds, ref / "requestBody"),
          fixed = None,
          default = None,
          constraint = "", // """[^/]+""", // TODO make this more sophisticated
          encode = false,
          place = ParameterPlace.BODY
        )
      ),
      request.headers.map(_.toMap).getOrElse(Map())
    )
  }

  def transform(response: HttpResponse, ref: naming.Reference): Response = {
    val responseRef = ref / response.statusCode.toString / "responseBody"
    Response(
      response.statusCode,
      responseRef,
      Parameter(
        name = "responseBody",
        typeName = transform(response.dataStructure.getOrElse { throw new IllegalStateException(s"A response is supposed to have a type. Response in question: $response, Path: $responseRef")}, responseRef),
        fixed = None,
        default = None,
        constraint = "", // """[^/]+""", // TODO make this more sophisticated
        encode = false,
        place = ParameterPlace.BODY
      ),
      response.headers.toMap
    )
  }

  def transform(vars: HrefVariables, ref: naming.Reference): List[(ParameterRef, Type)] = {
    vars.content.map({ member =>
      val r = (ref / escape(member.keyString.getOrElse { throw new IllegalStateException("A href variable needs a name") } ))
      ParameterRef(r) -> transform(member.value, emptyMeta, ref)
    })
  }

  def transform(transition: Transition, defaultPath: naming.Reference, packageName: String, controller: String):
      ResourceTranslation = {
    val href = transition.attributes.map(attr => Href(attr.href))
    val hrefRef = href.map(_.path).getOrElse(defaultPath)
    val ref = naming.Reference("paths") / hrefRef
    val initialReq = transform(transition.request, ref)
    val namespacedRef = ref / s"${initialReq.verb.name.toLowerCase}body"
    val req = transform(transition.request, namespacedRef)
    val resp = transform(transition.response, namespacedRef)
    val name = naming.Reference(namespacedRef.parts.init :+ escape(namespacedRef.parts.last))
    val vars = transition.attributes.map(a => transform(a.hrefVariables, namespacedRef)).getOrElse(List())
    val pathParameters: ParameterLookupTable = href.map(_.compile(vars)).getOrElse(Map())
    val responseRef = ParameterRef(resp.ref)
    val respParameter = Map(responseRef -> resp.parameter)
    val reqParameter = req.parameter.map(p => Map(ParameterRef(name) -> p)).getOrElse(Map())
    val handler = HandlerCall(
      packageName = packageName,
      controller = controller,
      instantiate = false, // Random guess
      method = req.verb.name.toLowerCase,
      parameters = (pathParameters ++ reqParameter).map(_._1).toSeq
    )
    val call = ApiCall(
      verb = req.verb,
      path = naming.Path(hrefRef),
      handler = handler,
      mimeIn = req.headers.get("Content-Type").map(x => Set(new Http.MimeType(x))).getOrElse(Set()),
      mimeOut = resp.headers.get("Content-Type").map(x => Set(new Http.MimeType(x))).getOrElse(Set()),
      errorMapping = Map(), // TODO
      resultTypes = Map(resp.statusCode -> responseRef),
      defaultResult = Some(responseRef)
    )
    val parameters = pathParameters ++ reqParameter ++ respParameter
    ResourceTranslation(List(call), parameters, parameters.map({case (ref, param) => ref.name -> param.typeName}))
  }

  def transform(resource: Resource, packageName: String): ResourceTranslation = {
    val href = Href(resource.href)
    val path = href.path
    val controller = escape(resource.meta.title) + "Controller"
    resource.content.map(c => transform(c, packageName, path, controller)).suml
  }

  def transform(content: ResourceContent, packageName: String, ref: naming.Reference, controller: String): ResourceTranslation = {
    content match {
      case so: DataStructure => {
        val typ = transform(so, ref / escape(controller))
        ResourceTranslation(List(), Map(), Map(typ.name -> typ))
      }
      case trans: Transition =>
        transform(trans, ref, packageName, controller)
      case category: Category =>
        transform(category, packageName)
      case copy: Copy =>
        mzero[ResourceTranslation]
      case resource: Resource =>
        transform(resource, packageName)
    }
  }

  def transform(category: Category, packageName: String): ResourceTranslation = {
    category.content.map(c => transform(c, packageName)).suml
  }

  def transform(content: CategoryContent, packageName: String): ResourceTranslation = {
    content match {
      case resource: Resource =>
        transform(resource, packageName)
      case category: Category =>
        transform(category, packageName)
      case copy: Copy =>
        mzero[ResourceTranslation]
    }
  }

  def transform(doc: ApibModel, packageName: String): ResourceTranslation = {
    doc.content.map(cat => transform(cat, packageName)).suml
  }

  def model(doc: ApibModel, packageName: String, basePath: String): StrictModel = {
    val translation = transform(doc, packageName) |+| pureObjectDef
    StrictModel(
      calls = translation.calls,
      typeDefs = translation.types,
      params = translation.parameters,
      discriminators = Map(),
      basePath = basePath,
      packageName = Some(packageName)
    )
  }
}

