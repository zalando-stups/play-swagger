package error_in_array.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._

import de.zalando.play.controllers.ArrayWrapper
object Generators {
    def createMetaCopyrightGenerator = _generate(MetaCopyrightGenerator)

    def createErrorsErrorsOptGenerator = _generate(ErrorsErrorsOptGenerator)

    def createModelSchemaSpecialDescriptionsGenerator = _generate(ModelSchemaSpecialDescriptionsGenerator)

    def createModelSchemaRootLinksGenerator = _generate(ModelSchemaRootLinksGenerator)

    def createErrorSourceGenerator = _generate(ErrorSourceGenerator)

    def createModelSchemaAgeGroupsGenerator = _generate(ModelSchemaAgeGroupsGenerator)

    def createModelSchemaRootDataGenerator = _generate(ModelSchemaRootDataGenerator)

    def createErrorsErrorsGenerator = _generate(ErrorsErrorsGenerator)

    def createModelSchemaRootMetaGenerator = _generate(ModelSchemaRootMetaGenerator)

    def MetaCopyrightGenerator = Gen.option(arbitrary[String])

    def ErrorsErrorsOptGenerator = _genList(ErrorGenerator, "csv")

    def ModelSchemaSpecialDescriptionsGenerator = Gen.option(ModelSchemaAgeGroupsGenerator)

    def ModelSchemaRootLinksGenerator = Gen.option(LinksGenerator)

    def ErrorSourceGenerator = Gen.option(ErrorSourceNameClashGenerator)

    def ModelSchemaAgeGroupsGenerator = _genList(arbitrary[String], "csv")

    def ModelSchemaRootDataGenerator = Gen.option(ModelSchemaGenerator)

    def ErrorsErrorsGenerator = Gen.option(ErrorsErrorsOptGenerator)

    def ModelSchemaRootMetaGenerator = Gen.option(MetaGenerator)

    def createModelSchemaRootGenerator = _generate(ModelSchemaRootGenerator)

    def createErrorsGenerator = _generate(ErrorsGenerator)

    def createErrorSourceNameClashGenerator = _generate(ErrorSourceNameClashGenerator)

    def createMetaGenerator = _generate(MetaGenerator)

    def createModelSchemaGenerator = _generate(ModelSchemaGenerator)

    def createErrorGenerator = _generate(ErrorGenerator)

    def createLinksGenerator = _generate(LinksGenerator)

    def ModelSchemaRootGenerator = for {
        data <- ModelSchemaRootDataGenerator
        meta <- ModelSchemaRootMetaGenerator
        links <- ModelSchemaRootLinksGenerator
        } yield ModelSchemaRoot(data, meta, links)

    def ErrorsGenerator = for {
        errors <- ErrorsErrorsGenerator
        } yield Errors(errors)

    def ErrorSourceNameClashGenerator = for {
        pointer <- MetaCopyrightGenerator
        parameter <- MetaCopyrightGenerator
        } yield ErrorSourceNameClash(pointer, parameter)

    def MetaGenerator = for {
        copyright <- MetaCopyrightGenerator
        } yield Meta(copyright)

    def ModelSchemaGenerator = for {
        name <- arbitrary[String]
        sizeRegister <- arbitrary[String]
        brand <- arbitrary[String]
        partnerArticleModelId <- arbitrary[Int]
        silhouetteId <- arbitrary[String]
        description <- MetaCopyrightGenerator
        ageGroups <- ModelSchemaAgeGroupsGenerator
        keywords <- MetaCopyrightGenerator
        lengthRegister <- MetaCopyrightGenerator
        specialDescriptions <- ModelSchemaSpecialDescriptionsGenerator
        articleModelAttributes <- ModelSchemaSpecialDescriptionsGenerator
        } yield ModelSchema(name, sizeRegister, brand, partnerArticleModelId, silhouetteId, description, ageGroups, keywords, lengthRegister, specialDescriptions, articleModelAttributes)

    def ErrorGenerator = for {
        source <- ErrorSourceGenerator
        code <- MetaCopyrightGenerator
        status <- MetaCopyrightGenerator
        detail <- MetaCopyrightGenerator
        title <- MetaCopyrightGenerator
        } yield Error(source, code, status, detail, title)

    def LinksGenerator = for {
        self <- MetaCopyrightGenerator
        related <- MetaCopyrightGenerator
        } yield Links(self, related)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample
    def _genList[T](gen: Gen[T], format: String): Gen[ArrayWrapper[T]] = for {
        items <- Gen.containerOf[List,T](gen)
    } yield ArrayWrapper(format)(items)
    }