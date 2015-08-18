package de.zalando.swagger

/**
 * @since 17.08.2015
 */
object ScalaReserved {
  val names = Seq("abstract", "case",
    "do", "for", "import", "lazy", "object", "override", "return", "sealed", "trait", "try",
    "var", "while", "class", "false", "if", "new", "package",
    "private", "super", "this", "true", "type",  "def", "final", "implicit", "null",
    "protected", "throw", "val", "_")

  val startNames = Seq("catch", "else", "extends", "finally", "forSome", "match", "with", "yield", "case")

  val partNames = Seq(",", ";", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@", "⇒", "←","+","-","[", ")", "]", "}")
}
