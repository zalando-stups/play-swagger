package error_in_array
package object yaml {
import de.zalando.play.controllers.ArrayWrapper
type ModelSchemaSpecialDescriptionsOpt = Seq[String]

    type MetaCopyright = Option[String]

    type ModelSchemaSpecialDescriptions = Option[ModelSchemaSpecialDescriptionsOpt]

    type ErrorsErrorsOpt = ArrayWrapper[Error]

    type ModelSchemaRootData = Option[ModelSchema]

    type ErrorSource = Option[ErrorSourceNameClash]

    type ModelSchemaArticleModelAttributesOpt = ArrayWrapper[String]

    type ModelSchemaRootLinks = Option[Links]

    type ModelSchemaArticleModelAttributes = Option[ModelSchemaArticleModelAttributesOpt]

    type ErrorsErrors = Option[ErrorsErrorsOpt]

    type ModelSchemaAgeGroups = ArrayWrapper[String]

    type ModelSchemaRootMeta = Option[Meta]

    case class ModelSchemaRoot(data: ModelSchemaRootData, meta: ModelSchemaRootMeta, links: ModelSchemaRootLinks) 

    case class Errors(errors: ErrorsErrors) 

    case class ErrorSourceNameClash(pointer: MetaCopyright, parameter: MetaCopyright) 

    case class Meta(copyright: MetaCopyright) 

    case class ModelSchema(name: String, sizeRegister: String, brand: String, partnerArticleModelId: Int, silhouetteId: String, description: MetaCopyright, ageGroups: ModelSchemaAgeGroups, keywords: MetaCopyright, lengthRegister: MetaCopyright, specialDescriptions: ModelSchemaSpecialDescriptions, articleModelAttributes: ModelSchemaArticleModelAttributes) 

    case class Error(source: ErrorSource, code: MetaCopyright, status: MetaCopyright, detail: MetaCopyright, title: MetaCopyright) 

    case class Links(self: MetaCopyright, related: MetaCopyright) 

    


    
    
    }
