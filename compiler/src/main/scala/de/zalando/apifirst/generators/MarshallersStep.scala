package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.{StrictModel, ApiCall}
import de.zalando.apifirst.ScalaName
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.play.controllers.WriterFactories

/**
  * @author  slasch
  * @since   30.12.2015.
  */
trait MarshallersStep extends EnrichmentStep[StrictModel] {

  override def steps = marshallers +: super.steps

  /**
    * Puts marshaller related information into the denotation table
    *
    * @return
    */
  protected val marshallers: SingleStep = spec => table => {
    val data = specMarshallers(spec._2)(table)
    if (data.nonEmpty)
      Map("marshallers" -> Map("data" -> data))
    else
      Map.empty
  }

  def specMarshallers(spec: StrictModel)(table: DenotationTable) = {
    val requiredPairs = for {
      call <- spec.calls
      mime <- call.mimeOut.map(_.name).diff(WriterFactories.factories.keys.toSet)
      resultTypeRef <- call.resultTypes.values ++ call.defaultResult.toSeq
      resultType = typeNameDenotation(table, resultTypeRef.name)
    } yield (mime, resultType)
    requiredPairs map { p =>
      val mime = p._1.replace('/', '_').replace('+', '_')
      Map(
        "mime_type" -> p._1,
        "result_type" -> p._2,
        "writable_name" -> ScalaName.escape(s"writable_${mime}_${p._2})")
      )
    }
  }

}
