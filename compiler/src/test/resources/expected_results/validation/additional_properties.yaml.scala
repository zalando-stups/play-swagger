package additional_properties.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class KeyedArraysValidator(override val instance: KeyedArrays) extends RecursiveValidator[KeyedArrays] {
        override val validators = Seq(
            new KeyedArraysAdditionalPropertiesValidator(instance.additionalProperties)
            )
        }
    class KeyedArraysAdditionalPropertiesValidator(override val instance: KeyedArraysAdditionalProperties) extends RecursiveValidator[KeyedArraysAdditionalProperties] {
        override val validators = Seq(
            )
        }
    class KeyedArraysAdditionalPropertiesCatchAllValidator(override val instance: KeyedArraysAdditionalPropertiesCatchAll) extends RecursiveValidator[KeyedArraysAdditionalPropertiesCatchAll] {
        override val validators = Seq(
            )
        }
    }
