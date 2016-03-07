package de.zalando.apifirst.generators

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.generators.DenotationNames._
import de.zalando.apifirst.{Security, ParameterPlace, ScalaName}
import de.zalando.play.controllers.WriterFactories

import scala.ref.WeakReference

/**
  * @author  slasch
  * @since   07.03.2016
  */
trait SecurityStep extends EnrichmentStep[StrictModel] with SecurityCommons {

  override def steps = security +: super.steps
  
  /**
    * Puts security related information into the denotation table
    *
    * @return
    */
  protected val security: SingleStep = spec => table => {
    val extractors: Iterable[Map[String, String]] = spec._2.securityDefinitionsTable.map { case (name, definition) =>
      Map("name" -> securityCheck(name))
    }
    if (extractors.isEmpty) Map.empty
    else Map("security_extractors" -> Map("extractors" -> extractors))
  }
}

trait SecurityCommons {
  def securityCheck(name: String) =
    ScalaName.escape(name + "_Extractor")

  def securityName(suffix: String, security: Security.Constraint*): String =
    security.map(_.name).mkString("", "_", "_"+suffix)
}