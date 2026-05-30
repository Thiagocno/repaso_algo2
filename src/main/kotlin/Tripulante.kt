package ar.edu.unsam.algo2

import java.time.LocalDate
import java.time.Period

class Tripulante (
    val nombre : String,
    val apellido : String,
    val fechaDeNacimiento : LocalDate,
    val misionesExitosas : Int,
    val misionesParciales : Int,
    val misionesFracasadas : Int,
    val fechaInicioActividad : LocalDate,
) {


    fun edadActual() : Int {
        return Period.between(fechaDeNacimiento, LocalDate.now()).years
    }

    fun aniosActivo() : Int {
        return LocalDate.now().year - fechaInicioActividad.year
    }

    fun expericencia() : Double {
        return aniosActivo().toDouble() +
                (misionesExitosas / 2.0) +
                (misionesParciales / 4.0) +
                (misionesFracasadas / 2.0)
    }
}