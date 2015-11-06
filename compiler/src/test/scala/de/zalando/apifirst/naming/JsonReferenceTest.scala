package de.zalando.apifirst.naming

import de.zalando.apifirst.new_naming.{Pointer, Index, Pointer$, Node}
import org.scalatest.{FunSpec, MustMatchers}

class JsonReferenceTest extends FunSpec with MustMatchers {
   describe("JsonReference") {

     it("must be created from a URI") {
       Pointer("").tokens mustBe Nil
     }
   }
 }
