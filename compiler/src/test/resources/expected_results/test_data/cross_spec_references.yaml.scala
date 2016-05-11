package cross_spec_references.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._
import de.zalando.play.controllers.ArrayWrapper
import scala.math.BigInt

object Generators {
    

    
    def createModelSchemaSpecialDescriptionsOptGenerator = _generate(ModelSchemaSpecialDescriptionsOptGenerator)
    def createMetaCopyrightGenerator = _generate(MetaCopyrightGenerator)
    def createModelSchemaKeywordsGenerator = _generate(ModelSchemaKeywordsGenerator)
    def createModelSchemaSpecialDescriptionsGenerator = _generate(ModelSchemaSpecialDescriptionsGenerator)
    def createModelSchemaRootDataGenerator = _generate(ModelSchemaRootDataGenerator)
    def createPetIdGenerator = _generate(PetIdGenerator)
    def createModelSchemaRootLinksGenerator = _generate(ModelSchemaRootLinksGenerator)
    def createPetTagsGenerator = _generate(PetTagsGenerator)
    def createPetPhotoUrlsGenerator = _generate(PetPhotoUrlsGenerator)
    def createModelSchemaLengthRegisterGenerator = _generate(ModelSchemaLengthRegisterGenerator)
    def createModelSchemaAgeGroupsGenerator = _generate(ModelSchemaAgeGroupsGenerator)
    def createPetCategoryGenerator = _generate(PetCategoryGenerator)
    def createPetTagsOptGenerator = _generate(PetTagsOptGenerator)
    def createModelSchemaRootMetaGenerator = _generate(ModelSchemaRootMetaGenerator)
    

    
    def ModelSchemaSpecialDescriptionsOptGenerator = _genList(arbitrary[String], "csv")
    def MetaCopyrightGenerator = Gen.option(arbitrary[String])
    def ModelSchemaKeywordsGenerator = Gen.option(arbitrary[String])
    def ModelSchemaSpecialDescriptionsGenerator = Gen.option(ModelSchemaSpecialDescriptionsOptGenerator)
    def ModelSchemaRootDataGenerator = Gen.option(ModelSchemaRootDataOptGenerator)
    def PetIdGenerator = Gen.option(arbitrary[Long])
    def ModelSchemaRootLinksGenerator = Gen.option(ModelSchemaRootLinksOptGenerator)
    def PetTagsGenerator = Gen.option(PetTagsOptGenerator)
    def PetPhotoUrlsGenerator = Gen.containerOf[List,String](arbitrary[String])
    def ModelSchemaLengthRegisterGenerator = Gen.option(arbitrary[String])
    def ModelSchemaAgeGroupsGenerator = _genList(arbitrary[String], "csv")
    def PetCategoryGenerator = Gen.option(PetCategoryOptGenerator)
    def PetTagsOptGenerator = Gen.containerOf[List,PetCategoryOpt](PetCategoryOptGenerator)
    def ModelSchemaRootMetaGenerator = Gen.option(ModelSchemaRootMetaOptGenerator)
    

    def createPetCategoryOptGenerator = _generate(PetCategoryOptGenerator)
    def createModelSchemaRootDataOptGenerator = _generate(ModelSchemaRootDataOptGenerator)
    def createModelSchemaRootMetaOptGenerator = _generate(ModelSchemaRootMetaOptGenerator)
    def createModelSchemaRootGenerator = _generate(ModelSchemaRootGenerator)
    def createPetGenerator = _generate(PetGenerator)
    def createModelSchemaRootLinksOptGenerator = _generate(ModelSchemaRootLinksOptGenerator)


    def PetCategoryOptGenerator = for {
        id <- PetIdGenerator
        name <- MetaCopyrightGenerator
    } yield PetCategoryOpt(id, name)
    def ModelSchemaRootDataOptGenerator = for {
        name <- arbitrary[String]
        sizeRegister <- arbitrary[String]
        brand <- arbitrary[String]
        partnerArticleModelId <- arbitrary[BigInt]
        silhouetteId <- arbitrary[String]
        description <- MetaCopyrightGenerator
        ageGroups <- ModelSchemaAgeGroupsGenerator
        keywords <- ModelSchemaKeywordsGenerator
        lengthRegister <- ModelSchemaLengthRegisterGenerator
        specialDescriptions <- ModelSchemaSpecialDescriptionsGenerator
        articleModelAttributes <- ModelSchemaSpecialDescriptionsGenerator
    } yield ModelSchemaRootDataOpt(name, sizeRegister, brand, partnerArticleModelId, silhouetteId, description, ageGroups, keywords, lengthRegister, specialDescriptions, articleModelAttributes)
    def ModelSchemaRootMetaOptGenerator = for {
        copyright <- MetaCopyrightGenerator
    } yield ModelSchemaRootMetaOpt(copyright)
    def ModelSchemaRootGenerator = for {
        data <- ModelSchemaRootDataGenerator
        meta <- ModelSchemaRootMetaGenerator
        links <- ModelSchemaRootLinksGenerator
    } yield ModelSchemaRoot(data, meta, links)
    def PetGenerator = for {
        name <- arbitrary[String]
        tags <- PetTagsGenerator
        photoUrls <- PetPhotoUrlsGenerator
        id <- PetIdGenerator
        status <- MetaCopyrightGenerator
        category <- PetCategoryGenerator
    } yield Pet(name, tags, photoUrls, id, status, category)
    def ModelSchemaRootLinksOptGenerator = for {
        self <- MetaCopyrightGenerator
        related <- MetaCopyrightGenerator
    } yield ModelSchemaRootLinksOpt(self, related)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
    
    
    
}