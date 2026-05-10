import ar.edu.unsam.algo2.Perro
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PerroTest : DescribeSpec ({
  describe("Tests de perro"){
      it(""){
          val perro = Perro()
          perro.ladrar() shouldBe "guau"
      }
  }
})