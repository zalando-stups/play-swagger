package de.zalando.play.controllers

import play.api.http.Writeable

case class WriteableWrapper[T](w: Writeable[T], m: Manifest[T])

object WriteableWrapper {
  implicit def writeable2wrapper[T](w: Writeable[T])(implicit m: Manifest[T]) =
    WriteableWrapper(w, m)
}

/**
  * @since 28.02.2016.
  */
object ResponseWriters extends ResponseWritersBase

trait ResponseWritersBase {

  type ContentType = String

  def custom: Seq[WriteableWrapper[_]] = Seq.empty

  case class choose[T](mimeType: ContentType) {
    def apply[R <: Any](registry: Seq[WriteableWrapper[_]] = custom)(implicit m: Manifest[R]): Option[Writeable[R]] =
      registry filter {
        _.w.contentType.exists(_ == mimeType)
      } find { p =>
        m.runtimeClass.isAssignableFrom(p.m.runtimeClass)
      } map  {
        _.asInstanceOf[WriteableWrapper[R]]
      } map(_.w)
  }

}