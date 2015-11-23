package full.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    import java.util.Date
    class PetNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetNameValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new PetNameConstraints(instance))
    }
    class OrderQuantityValidator(override val instance: OrderQuantity) extends RecursiveValidator[OrderQuantity] {
        override val validators = Seq(
            )
        }
    class OrderPetIdValidator(override val instance: OrderPetId) extends RecursiveValidator[OrderPetId] {
        override val validators = Seq(
            )
        }
    class UserValidator(override val instance: User) extends RecursiveValidator[User] {
        override val validators = Seq(
            new OrderStatusValidator(instance.email), 
            new OrderStatusValidator(instance.username), 
            new OrderQuantityValidator(instance.userStatus), 
            new OrderStatusValidator(instance.lastName), 
            new OrderStatusValidator(instance.firstName), 
            new OrderPetIdValidator(instance.id), 
            new OrderStatusValidator(instance.phone), 
            new OrderStatusValidator(instance.password)
            )
        }
    class OrderValidator(override val instance: Order) extends RecursiveValidator[Order] {
        override val validators = Seq(
            new OrderShipDateValidator(instance.shipDate), 
            new OrderQuantityValidator(instance.quantity), 
            new OrderPetIdValidator(instance.petId), 
            new OrderPetIdValidator(instance.id), 
            new OrderCompleteValidator(instance.complete), 
            new OrderStatusValidator(instance.status)
            )
        }
    class TagValidator(override val instance: Tag) extends RecursiveValidator[Tag] {
        override val validators = Seq(
            new OrderPetIdValidator(instance.id), 
            new OrderStatusValidator(instance.name)
            )
        }
    class OrderStatusValidator(override val instance: OrderStatus) extends RecursiveValidator[OrderStatus] {
        override val validators = Seq(
            )
        }
    class PetTagsValidator(override val instance: PetTags) extends RecursiveValidator[PetTags] {
        override val validators = Seq(
            )
        }
    class OrderCompleteValidator(override val instance: OrderComplete) extends RecursiveValidator[OrderComplete] {
        override val validators = Seq(
            )
        }
    class PetCategoryValidator(override val instance: PetCategory) extends RecursiveValidator[PetCategory] {
        override val validators = Seq(
            )
        }
    class OrderShipDateValidator(override val instance: OrderShipDate) extends RecursiveValidator[OrderShipDate] {
        override val validators = Seq(
            )
        }
    class PetValidator(override val instance: Pet) extends RecursiveValidator[Pet] {
        override val validators = Seq(
            new PetNameValidator(instance.name), 
            new PetTagsValidator(instance.tags), 
            new OrderStatusValidator(instance.photoUrls), 
            new OrderPetIdValidator(instance.id), 
            new OrderStatusValidator(instance.status), 
            new PetCategoryValidator(instance.category)
            )
        }
    }
object pathsValidator {
    import definitions._
    import paths._
    import definitionsValidator._
    class UsersUsernamGetUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernamGetUsernameValidator(override val instance: String) extends RecursiveValidator[String] {
      override val validators = Seq(new UsersUsernamGetUsernameConstraints(instance))
    }
    class UsersCreateWithListPostResponsesDefaultConstraints(override val instance: Null) extends ValidationBase[Null] {
        override def constraints: Seq[Constraint[Null]] =
        Seq()
    }
    class UsersCreateWithListPostResponsesDefaultValidator(override val instance: Null) extends RecursiveValidator[Null] {
      override val validators = Seq(new UsersCreateWithListPostResponsesDefaultConstraints(instance))
    }
    class PetsPetIDeletePetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsPetIDeletePetIdValidator(override val instance: Long) extends RecursiveValidator[Long] {
      override val validators = Seq(new PetsPetIDeletePetIdConstraints(instance))
    }
    class PetsFindByStatusGetResponses200Validator(override val instance: PetsFindByStatusGetResponses200) extends RecursiveValidator[PetsFindByStatusGetResponses200] {
        override val validators = Seq(
            )
        }
    class PetsPostBodyValidator(override val instance: PetsPostBody) extends RecursiveValidator[PetsPostBody] {
        override val validators = Seq(
            )
        }
    class UsersUsernamPutBodyValidator(override val instance: UsersUsernamPutBody) extends RecursiveValidator[UsersUsernamPutBody] {
        override val validators = Seq(
            )
        }
    class StoresOrderPostBodyValidator(override val instance: StoresOrderPostBody) extends RecursiveValidator[StoresOrderPostBody] {
        override val validators = Seq(
            )
        }
    class UsersCreateWithListPostBodyValidator(override val instance: UsersCreateWithListPostBody) extends RecursiveValidator[UsersCreateWithListPostBody] {
        override val validators = Seq(
            )
        }
    class PetsFindByStatusGetStatusValidator(override val instance: PetsFindByStatusGetStatus) extends RecursiveValidator[PetsFindByStatusGetStatus] {
        override val validators = Seq(
            )
        }
    }
