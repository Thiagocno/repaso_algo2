package ar.edu.unsam.algo2

import ar.edu.unsam.algo2.Perro
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PerroTest : DescribeSpec ({
  describe("Tests de perro"){
      it("Perro dice guau"){
          val perro = Perro()
          perro.ladrar() shouldBe "guau"
      }
  }
})