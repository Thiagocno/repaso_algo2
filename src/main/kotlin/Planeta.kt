package ar.edu.unsam.algo2

import ar.edu.unsam.algo2.repositorios.ID
import java.time.LocalDate

class Planeta (
    val nombre: String,
    val temperaturaMedia : Double,
    val gravedad: Double,
    val nivelDeRadiacion: Int,
    val tieneAguaLiquida: Boolean,
    val toxicidadAtmosferica: Int,
    val actividadTectonica: Int,
    val tamaño: Double,
    val fechaDeDescubrimiento: LocalDate,
    val distanciaATierra: Double,
    var fueAterrizado: Boolean = false,
) : ID{
    override var id: Int = 0

    fun esValido() : Boolean{
        if (nombre.isBlank()) {
            throw Exception("El nombre no puede estar vacio")
        }
        if (nivelDeRadiacion < 0 || nivelDeRadiacion > 100 ) {
            throw Exception("El nivel de radiacion no es valido")
        }
        if (toxicidadAtmosferica < 0 || toxicidadAtmosferica > 100 ) {
            throw Exception("El nivel de toxicidad no es valido")
        }
        if (gravedad < 0) {
            throw Exception("La gravedad del planeta no es valida")
        }
        if (tamaño < 0) {
            throw Exception("El tamaño del planeta no es valido")
        }
        return true
    }

    fun tieneTemperaturaIdeal() = temperaturaMedia >= 0 && temperaturaMedia <= 40

    fun tieneGravedadSoportable() = gravedad >= 3 && gravedad <= 15

    fun esHabitable() : Boolean{
        return tieneTemperaturaIdeal() &&
                tieneGravedadSoportable() &&
                tieneAguaLiquida &&
                toxicidadAtmosferica < 30 &&
                nivelDeRadiacion < 40
    }

    fun indiceDePeligrosidad() : Int {
        return (nivelDeRadiacion + toxicidadAtmosferica + actividadTectonica) / 3
    }

    fun esExplorable(): Boolean {
        return !esHabitable() && indiceDePeligrosidad() < 60
    }

}