package options.yaml
import scala.collection.Seq
import scala.Option
object definitions {
  case class Basic(
    id: Long,
    required: Seq[String],
    optional: Option[Seq[String]]
  )
}