package options.yaml
object definitions {
    type BasicRequired = scala.collection.Seq[String]
    type BasicOptional = Option[BasicRequired]
    case class Basic(id: Long, required: BasicRequired, optional: BasicOptional) 
    }
