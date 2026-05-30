package ar.edu.unsam.algo2

import ar.edu.unsam.algo2.Tripulante
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class TripulanteTests : DescribeSpec ({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests de tripulante") {

        it("Calculo de experiencia"){

            val tripulanteExperimentado = Tripulante(nombre = "Thiago",
                apellido = "Cornes",
                fechaDeNacimiento = LocalDate.of(2006, 3, 28),
                misionesExitosas = 2,
                misionesFracasadas = 5,
                misionesParciales = 3,
                fechaInicioActividad = LocalDate.of(2024, 3, 28)
            )

            val experiencia = tripulanteExperimentado.expericencia()

            experiencia shouldBe 6.25
        }

        it("Experiencia no es nula"){

            val tripulante = Tripulante(nombre = "Thiago",
                apellido = "Cornes",
                fechaDeNacimiento = LocalDate.of(2006, 3, 28),
                misionesExitosas = 0,
                misionesFracasadas = 0,
                misionesParciales = 0,
                fechaInicioActividad = LocalDate.of(2024, 3, 28)
            )

            val experiencia = tripulante.expericencia()

            experiencia shouldNotBe null
        }

        it("Experiencia no es negativa"){

            val tripulante = Tripulante(nombre = "Thiago",
                apellido = "Cornes",
                fechaDeNacimiento = LocalDate.of(2006, 3, 28),
                misionesExitosas = 0,
                misionesFracasadas = 0,
                misionesParciales = 0,
                fechaInicioActividad = LocalDate.of(2024, 3, 28)
            )

            val experiencia = tripulante.expericencia()

            experiencia shouldBeGreaterThanOrEqual 0.0
        }

        it("Calcula bien edad"){

            val tripulante = Tripulante(nombre = "Thiago",
                apellido = "Cornes",
                fechaDeNacimiento = LocalDate.of(2006, 3, 28),
                misionesExitosas = 0,
                misionesFracasadas = 0,
                misionesParciales = 0,
                fechaInicioActividad = LocalDate.of(2024, 3, 28)
            )

            tripulante.edadActual() shouldBe (LocalDate.now().year - tripulante.fechaDeNacimiento.year)
        }
    }


})