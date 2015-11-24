package additional_properties.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    class KeyedArraysValidator(instance: KeyedArrays) extends RecursiveValidator {
        override val validators = Seq(
            new KeyedArraysAdditionalPropertiesValidator(instance.additionalProperties)
            )
        }
    class KeyedArraysAdditionalPropertiesValidator(instance: KeyedArraysAdditionalProperties) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class KeyedArraysAdditionalPropertiesCatchAllValidator(instance: KeyedArraysAdditionalPropertiesCatchAll) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    }
