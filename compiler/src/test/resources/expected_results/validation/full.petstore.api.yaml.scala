package full.petstore.api.yaml
import play.api.mvc.{Action, Controller}
import play.api.data.validation.Constraint
import de.zalando.play.controllers._
import PlayBodyParsing._
import PlayValidations._

object definitionsValidator {
    import definitions._
    import java.util.Date
    import pathsValidator._
    class UsersUsernameGetUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernameGetUsernameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersUsernameGetUsernameConstraints(instance))
    }
    class PetsPetIdPostStatusConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdPostStatusValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdPostStatusConstraints(instance))
    }
    class PetsPetIdPostPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdPostPetIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdPostPetIdConstraints(instance))
    }
    class PetsPetIdDeletePetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsPetIdDeletePetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdDeletePetIdConstraints(instance))
    }
    class PetsPetIdDeleteApi_keyConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdDeleteApi_keyValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdDeleteApi_keyConstraints(instance))
    }
    class PetsPetIdPostNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdPostNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdPostNameConstraints(instance))
    }
    class UsersUsernamePutUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernamePutUsernameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersUsernamePutUsernameConstraints(instance))
    }
    class PetsPetIdGetPetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsPetIdGetPetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdGetPetIdConstraints(instance))
    }
    class StoresOrderOrderIdDeleteOrderIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class StoresOrderOrderIdDeleteOrderIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new StoresOrderOrderIdDeleteOrderIdConstraints(instance))
    }
    class StoresOrderOrderIdGetOrderIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class StoresOrderOrderIdGetOrderIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new StoresOrderOrderIdGetOrderIdConstraints(instance))
    }
    class UsersUsernameDeleteUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernameDeleteUsernameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersUsernameDeleteUsernameConstraints(instance))
    }
    }
object pathsValidator {
    import definitions._
    import paths._
    import definitionsValidator.OrderStatusValidator
    class UsersUsernameGetUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernameGetUsernameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersUsernameGetUsernameConstraints(instance))
    }
    class PetsPetIdPostStatusConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdPostStatusValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdPostStatusConstraints(instance))
    }
    class PetsPetIdPostPetIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdPostPetIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdPostPetIdConstraints(instance))
    }
    class PetsPetIdDeletePetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsPetIdDeletePetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdDeletePetIdConstraints(instance))
    }
    class PetsPetIdDeleteApi_keyConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdDeleteApi_keyValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdDeleteApi_keyConstraints(instance))
    }
    class PetsPetIdPostNameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class PetsPetIdPostNameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdPostNameConstraints(instance))
    }
    class UsersUsernamePutUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernamePutUsernameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersUsernamePutUsernameConstraints(instance))
    }
    class PetsPetIdGetPetIdConstraints(override val instance: Long) extends ValidationBase[Long] {
        override def constraints: Seq[Constraint[Long]] =
        Seq()
    }
    class PetsPetIdGetPetIdValidator(instance: Long) extends RecursiveValidator {
      override val validators = Seq(new PetsPetIdGetPetIdConstraints(instance))
    }
    class StoresOrderOrderIdDeleteOrderIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class StoresOrderOrderIdDeleteOrderIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new StoresOrderOrderIdDeleteOrderIdConstraints(instance))
    }
    class StoresOrderOrderIdGetOrderIdConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class StoresOrderOrderIdGetOrderIdValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new StoresOrderOrderIdGetOrderIdConstraints(instance))
    }
    class UsersUsernameDeleteUsernameConstraints(override val instance: String) extends ValidationBase[String] {
        override def constraints: Seq[Constraint[String]] =
        Seq()
    }
    class UsersUsernameDeleteUsernameValidator(instance: String) extends RecursiveValidator {
      override val validators = Seq(new UsersUsernameDeleteUsernameConstraints(instance))
    }
    class PetsPostBodyValidator(instance: PetsPostBody) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class UsersUsernamePutBodyValidator(instance: UsersUsernamePutBody) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class StoresOrderPostBodyValidator(instance: StoresOrderPostBody) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class UsersCreateWithListPostBodyValidator(instance: UsersCreateWithListPostBody) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsFindByStatusGetStatusValidator(instance: PetsFindByStatusGetStatus) extends RecursiveValidator {
        override val validators = Seq(
            )
        }
    class PetsFindByTagsGetValidator(tags: PetsFindByStatusGetStatus) extends RecursiveValidator {
    override val validators = Seq(
    new PetsFindByStatusGetStatusValidator(tags)
    )
    }
    class StoresOrderPostValidator(body: StoresOrderPostBody) extends RecursiveValidator {
    override val validators = Seq(
    new StoresOrderPostBodyValidator(body)
    )
    }
    class UsersPostValidator(body: UsersUsernamePutBody) extends RecursiveValidator {
    override val validators = Seq(
    new UsersUsernamePutBodyValidator(body)
    )
    }
    class UsersCreateWithListPostValidator(body: UsersCreateWithListPostBody) extends RecursiveValidator {
    override val validators = Seq(
    new UsersCreateWithListPostBodyValidator(body)
    )
    }
    class UsersUsernameGetValidator(username: String) extends RecursiveValidator {
    override val validators = Seq(
    new UsersUsernameGetUsernameValidator(username)
    )
    }
    class UsersUsernamePutValidator(username: String, body: UsersUsernamePutBody) extends RecursiveValidator {
    override val validators = Seq(
    new UsersUsernamePutUsernameValidator(username), 
    new UsersUsernamePutBodyValidator(body)
    )
    }
    class UsersUsernameDeleteValidator(username: String) extends RecursiveValidator {
    override val validators = Seq(
    new UsersUsernameDeleteUsernameValidator(username)
    )
    }
    class PetsPutValidator(body: PetsPostBody) extends RecursiveValidator {
    override val validators = Seq(
    new PetsPostBodyValidator(body)
    )
    }
    class PetsPostValidator(body: PetsPostBody) extends RecursiveValidator {
    override val validators = Seq(
    new PetsPostBodyValidator(body)
    )
    }
    class UsersCreateWithArrayPostValidator(body: UsersCreateWithListPostBody) extends RecursiveValidator {
    override val validators = Seq(
    new UsersCreateWithListPostBodyValidator(body)
    )
    }
    class StoresOrderOrderIdGetValidator(orderId: String) extends RecursiveValidator {
    override val validators = Seq(
    new StoresOrderOrderIdGetOrderIdValidator(orderId)
    )
    }
    class StoresOrderOrderIdDeleteValidator(orderId: String) extends RecursiveValidator {
    override val validators = Seq(
    new StoresOrderOrderIdDeleteOrderIdValidator(orderId)
    )
    }
    class PetsPetIdGetValidator(petId: Long) extends RecursiveValidator {
    override val validators = Seq(
    new PetsPetIdGetPetIdValidator(petId)
    )
    }
    class PetsPetIdPostValidator(petId: String, name: String, status: String) extends RecursiveValidator {
    override val validators = Seq(
    new PetsPetIdPostPetIdValidator(petId), 
    new PetsPetIdPostNameValidator(name), 
    new PetsPetIdPostStatusValidator(status)
    )
    }
    class PetsPetIdDeleteValidator(api_key: String, petId: Long) extends RecursiveValidator {
    override val validators = Seq(
    new PetsPetIdDeleteApi_keyValidator(api_key), 
    new PetsPetIdDeletePetIdValidator(petId)
    )
    }
    class PetsFindByStatusGetValidator(status: PetsFindByStatusGetStatus) extends RecursiveValidator {
    override val validators = Seq(
    new PetsFindByStatusGetStatusValidator(status)
    )
    }
    class UsersLoginGetValidator(username: OrderStatus, password: OrderStatus) extends RecursiveValidator {
    override val validators = Seq(
    new OrderStatusValidator(username), 
    new OrderStatusValidator(password)
    )
    }
    }
