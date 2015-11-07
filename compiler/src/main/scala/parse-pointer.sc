def parse(s: String): Seq[String] = {
  def recurse(s: String, acc: (Option[String], Seq[String])): Seq[String] = {
    s.headOption match {
      case Some('/') => acc._1 match {
        case Some(token) => recurse(s.tail, (Some(""), acc._2 :+ token))
        case None        => recurse(s.tail, (Some(""), acc._2))
      }
      case Some(c)   => acc._1 match {
        case Some(token) => recurse(s.tail, (Some(token :+ c), acc._2))
        case None        => recurse(s.tail, (Some(c.toString), acc._2))
      }
      case None      => acc._1 match {
        case Some(token) => acc._2 :+ token
        case None        => acc._2
      }
    }
  }
  recurse(s, (None,Seq()))
}

parse("/")
