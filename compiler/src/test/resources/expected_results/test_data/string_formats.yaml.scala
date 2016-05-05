package string_formats.yaml

import org.scalacheck.Gen
import org.scalacheck.Arbitrary
import Arbitrary._
import de.zalando.play.controllers.Base64String
import Base64String._
import de.zalando.play.controllers.BinaryString
import BinaryString._
import org.joda.time.DateTime
import org.joda.time.LocalDate

object Generators {
    

    
    def createGetBase64Generator = _generate(GetBase64Generator)
    def createBinaryStringGenerator = _generate(BinaryStringGenerator)
    def createGetDate_timeGenerator = _generate(GetDate_timeGenerator)
    def createGetDateGenerator = _generate(GetDateGenerator)
    def createNullGenerator = _generate(NullGenerator)
    

    
    def GetBase64Generator = Gen.option(arbitrary[Base64String])
    def BinaryStringGenerator = arbitrary[BinaryString]
    def GetDate_timeGenerator = Gen.option(arbitrary[DateTime])
    def GetDateGenerator = Gen.option(arbitrary[LocalDate])
    def NullGenerator = arbitrary[Null]
    


    def _generate[T](gen: Gen[T]) = (count: Int) => for (i <- 1 to count) yield gen.sample

    
    implicit lazy val arbLocalDate: Arbitrary[LocalDate] = Arbitrary(for {
        l <- arbitrary[Long]
    } yield new LocalDate(System.currentTimeMillis + l))
    
    implicit lazy val arbDateTime: Arbitrary[DateTime] = Arbitrary(for {
        l <- arbitrary[Long]
    } yield new DateTime(System.currentTimeMillis + l))
    
    implicit lazy val arbBinaryString: Arbitrary[BinaryString] = Arbitrary(for {
        s <- arbitrary[String]
    } yield BinaryString.fromString(s))
    
    implicit lazy val arbBase64String: Arbitrary[Base64String] = Arbitrary(for {
        s <- arbitrary[String]
    } yield Base64String.string2base64string(s))
    
}