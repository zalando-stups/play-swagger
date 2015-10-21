package de.zalando.swagger

import de.zalando.apifirst.Domain._
import de.zalando.swagger.strictModel._

/**
 * @author  slasch 
 * @since   15.10.2015.
 */
object TypeMetaConverter {
  import ValidationsConverter._

  implicit def arrayTypeMeta[T](comment: String, items: ArrayValidation[T]): TypeMeta =
    TypeMeta(Option(comment), toArrayValidations(items))

  implicit def schemaTypeMeta[T](param: Schema[_]) =
    TypeMeta(Option(param.description).orElse(Option(param.format)), toValidations(param))

  implicit def parametersListItemMeta(item:ParametersListItem): TypeMeta =
    item match {
      case r @ JsonReference(ref) => TypeMeta(Some(ref))
      case nb: NonBodyParameterCommons[_, _] =>
        TypeMeta(Option(nb.format), toValidations(nb))
      case bp: BodyParameter[_] =>
        TypeMeta(Option(bp.description), toValidations(bp))
      case nbp: NonBodyParameter[_] =>
        TypeMeta(Option(nbp.name), toValidations(nbp))

    }
}
