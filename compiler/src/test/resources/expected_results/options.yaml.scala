package options.yaml
import scala.Option
import scala.collection.Seq
object definitions {
  case class Basic(
    id: Long,
    required: Seq[String],
    optional: Option[Seq[String]]
  )
}