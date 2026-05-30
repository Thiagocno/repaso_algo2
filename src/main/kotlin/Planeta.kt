package ar.edu.unsam.algo2

import java.time.LocalDate

class Planeta (
    val nombre: String,
    val temperaturaMedia : Double,
    val gravedad: Double,
    val nivelDeRadiacion: Int,
    val tieneAguaLiquida: Boolean,
    val toxicidadAdmosferica: Int,
    val actividadTectonica: Int,
    val tamaño: Double,
    val fechaDeDescubrimiento: LocalDate,
    val distanciaATierra: Double,
    val fueAterrizado: Boolean,
){

    fun tieneTemperaturaIdeal() = temperaturaMedia >= 0 && temperaturaMedia <= 40

    fun tieneGravedadSoportable() = gravedad >= 3 && gravedad <= 15

}