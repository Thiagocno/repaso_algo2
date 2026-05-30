package ar.edu.unsam.algo2

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class PlanetaTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests generales de planeta"){

        it("Tiene temperatura ideal (entre 0 y 40"){
            val planetaTemperaturaIdeal = Planeta(
                nombre = "Adrian",
                temperaturaMedia = 15.0,
                gravedad = 10.0,
                nivelDeRadiacion = 10,
                tieneAguaLiquida = true,
                toxicidadAdmosferica = 10,
                actividadTectonica = 10,
                tamaño = 10.0,
                fechaDeDescubrimiento = LocalDate.of(2026, 3, 28),
                distanciaATierra = 100.0,
                fueAterrizado = true,
            )

            planetaTemperaturaIdeal.tieneTemperaturaIdeal() shouldBe true
        }

        it("Tiene gravedad ideal (entre 3 y 15"){
            val planetaGravedadIdeal = Planeta(
                nombre = "Adrian",
                temperaturaMedia = 15.0,
                gravedad = 10.0,
                nivelDeRadiacion = 10,
                tieneAguaLiquida = true,
                toxicidadAdmosferica = 10,
                actividadTectonica = 10,
                tamaño = 10.0,
                fechaDeDescubrimiento = LocalDate.of(2026, 3, 28),
                distanciaATierra = 100.0,
                fueAterrizado = true,
            )

            planetaGravedadIdeal.tieneTemperaturaIdeal() shouldBe true
        }

    }
})


