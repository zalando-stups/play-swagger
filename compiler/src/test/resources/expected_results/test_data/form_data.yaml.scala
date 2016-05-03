package form_data.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._
import java.io.File

object Generators {
    

    
    def createMultipartPostAvatarGenerator = _generate(MultipartPostAvatarGenerator)
    def createBothPostResponses200NameGenerator = _generate(BothPostResponses200NameGenerator)
    def createStringGenerator = _generate(StringGenerator)
    def createBothPostYearGenerator = _generate(BothPostYearGenerator)
    def createFileGenerator = _generate(FileGenerator)
    

    
    def MultipartPostAvatarGenerator = Gen.option(arbitrary[File])
    def BothPostResponses200NameGenerator = Gen.option(arbitrary[String])
    def StringGenerator = arbitrary[String]
    def BothPostYearGenerator = Gen.option(arbitrary[Int])
    def FileGenerator = arbitrary[File]
    

    def createMultipartPostResponses200Generator = _generate(MultipartPostResponses200Generator)
    def createBothPostResponses200Generator = _generate(BothPostResponses200Generator)


    def MultipartPostResponses200Generator = for {
        name <- BothPostResponses200NameGenerator
        year <- BothPostYearGenerator
        fileSize <- BothPostYearGenerator
        fileName <- BothPostResponses200NameGenerator
    } yield MultipartPostResponses200(name, year, fileSize, fileName)
    def BothPostResponses200Generator = for {
        name <- BothPostResponses200NameGenerator
        year <- BothPostYearGenerator
        avatarSize <- BothPostYearGenerator
        ringtoneSize <- BothPostYearGenerator
    } yield BothPostResponses200(name, year, avatarSize, ringtoneSize)

    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    
    
    
    implicit lazy val arbFile: Arbitrary[File] = Arbitrary(for {
        prefix <- arbitrary[Int]
        suffix <- arbitrary[Int]
        content <- arbitrary[String]
        file = {
            val f = File.createTempFile(prefix.toString, suffix.toString)
            f.deleteOnExit()
            import java.nio.file.{Paths, Files}
            import java.nio.charset.StandardCharsets
            Files.write(Paths.get(f.getAbsolutePath), content.getBytes(StandardCharsets.UTF_8))
            f
        }
    } yield file)
}