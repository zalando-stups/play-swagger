package de.zalando.swagger

import java.io.File
import java.net.URI

import de.zalando.apifirst.Application.StrictModel
import de.zalando.apifirst.Domain.Type
import de.zalando.apifirst.naming.Reference
import de.zalando.swagger.strictModel.SwaggerModel

/**
 * @author  slasch
 * @since   20.10.2015.
 */
trait ParameterNaming {
  type Types = Seq[Type]
  type NamedType = (Reference, Type)
  type NamedTypes = Seq[NamedType]
}

object ModelConverter extends ParameterNaming {

  def fromModel(base: URI, model: SwaggerModel, file: Option[File] = None, keyPrefix: String = "x-api-first", autoConvert: Boolean = true) = {
    val converter = new TypeConverter(base, model, keyPrefix)
    val typeDefs = converter.convert
    val discriminators = converter.discriminators.toMap
    val inlineParameters = new ParametersConverter(base, model, keyPrefix, typeDefs, autoConvert).parameters
    val apiCalls = new PathsConverter(base, model, keyPrefix, inlineParameters, file.map(_.getName)).convert
    val packageName = model.vendorExtensions.get(s"$keyPrefix-package")
    val inheritedPackageName = apiCalls.headOption collect {
      case h if apiCalls.seq.forall { _.handler.packageName == h.handler.packageName } => h.handler.packageName
    }
    StrictModel(apiCalls, typeDefs.toMap, inlineParameters, discriminators, model.basePath, packageName orElse inheritedPackageName)
  }

}
