package de.zalando

import java.io.{FileOutputStream, File}

import scala.io.Source

/**
 * @since 19.08.2015
 */
trait ExpectedResults {

  def dump(result: String, file: File, suffix: String): Unit = {
    if (result.nonEmpty) {
      val newFile = target(file, suffix)
      newFile.getParentFile.mkdirs()
      newFile.delete()
      newFile.createNewFile()
      val out = new FileOutputStream(newFile)
      out.write(result.getBytes)
      out.close
    }
  }

  def asInFile(file: File, suffix: String): String = {
    val expectedFile = target(file, suffix)
    if (expectedFile.canRead)
      Source.fromFile(expectedFile).getLines().mkString("\n")
    else
      ""
  }

  def target(file:File, suffix: String) =
    new File(file.getParentFile.getParent + "/expected_results/" + file.getName + "." + suffix)
}
