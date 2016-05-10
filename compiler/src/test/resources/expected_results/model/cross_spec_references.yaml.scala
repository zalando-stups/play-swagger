package cross_spec_references

package object yaml {

    import de.zalando.play.controllers.ArrayWrapper



    type ModelSchemaSpecialDescriptionsOpt = ArrayWrapper[String]
    type MetaCopyright = Option[String]
    type ModelSchemaKeywords = Option[String]
    type ModelSchemaSpecialDescriptions = Option[ModelSchemaSpecialDescriptionsOpt]
    type ModelSchemaRootData = Option[ModelSchemaRootDataOpt]
    type PetId = Option[Long]
    type ModelSchemaRootLinks = Option[ModelSchemaRootLinksOpt]
    type PetTags = Option[PetTagsOpt]
    type PetPhotoUrls = Seq[String]
    type ModelSchemaLengthRegister = Option[String]
    type ModelSchemaAgeGroups = ArrayWrapper[String]
    type PetCategory = Option[PetCategoryOpt]
    type PetTagsOpt = Seq[PetCategoryOpt]
    type ModelSchemaRootMeta = Option[ModelSchemaRootMetaOpt]


    case class PetCategoryOpt(id: PetId, name: MetaCopyright) 
    case class ModelSchemaRootDataOpt(name: String, sizeRegister: String, brand: String, partnerArticleModelId: Int, silhouetteId: String, description: MetaCopyright, ageGroups: ModelSchemaAgeGroups, keywords: ModelSchemaKeywords, lengthRegister: ModelSchemaLengthRegister, specialDescriptions: ModelSchemaSpecialDescriptions, articleModelAttributes: ModelSchemaSpecialDescriptions) 
    case class ModelSchemaRootMetaOpt(copyright: MetaCopyright) 
    case class ModelSchemaRoot(data: ModelSchemaRootData, meta: ModelSchemaRootMeta, links: ModelSchemaRootLinks) 
    case class Pet(name: String, tags: PetTags, photoUrls: PetPhotoUrls, id: PetId, status: MetaCopyright, category: PetCategory) 
    case class ModelSchemaRootLinksOpt(self: MetaCopyright, related: MetaCopyright) 


}
