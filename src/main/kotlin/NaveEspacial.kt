package ar.edu.unsam.algo2

import java.time.LocalDate
import java.time.LocalDateTime

abstract class NaveEspacial(
    val nombre: String,
    val codigoDeIdentificacion: Int,
    val fechaDeFabricacion: LocalDate,
    val velocidadPromedio: Double,
    val autonomia: Double,
    val consumoBase: Double,
    var seEncuentraEnMision: Boolean = false,
    val baseAsignada: BaseDeLanzamiento,
    ) {

    public fun esValido() : Boolean{
        if (nombre.isBlank()) {
            throw Exception("El nombre no puede estar vacio")
        }
        if (codigoDeIdentificacion == null) {
            throw Exception("El codigo de identificacion no puede estar vacio")
        }
        if (velocidadPromedio <= 0) {
            throw Exception("La velocidad promedio debe ser mayor a 0")
        }
        if (autonomia <= 0) {
            throw Exception("La autonomia debe ser mayor a 0")
        }
        if (consumoBase <= 0) {
            throw Exception("El consumo base debe ser mayor a 0")
        }
        return true
    }

    fun antiguedad() : Int{
        return LocalDateTime.now().year - fechaDeFabricacion.year
    }

    fun puedeAlcanzarPlaneta(planetaDestino : Planeta) : Boolean{
        return ((planetaDestino.distanciaATierra * 365) / (velocidadPromedio * 2)) <= autonomia
    }

    fun esModerna() : Boolean{
        return antiguedad() < 5
    }

    abstract fun consumoAniosLuz(mision: Mision) : Double

    fun consumoTotal(distanciaAlPlaneta : Double, mision : Mision) : Double{
        return consumoAniosLuz(mision) * distanciaAlPlaneta
    }
}

class Sonda(
    nombre: String,
    codigoDeIdentificacion: Int,
    fechaDeFabricacion: LocalDate,
    velocidadPromedio: Double,
    autonomia: Double,
    consumoBase: Double,
    seEncuentraEnMision: Boolean,
    baseAsignada: BaseDeLanzamiento,
) : NaveEspacial(nombre, codigoDeIdentificacion, fechaDeFabricacion, velocidadPromedio, autonomia, consumoBase, seEncuentraEnMision, baseAsignada) {

    override fun consumoAniosLuz(mision: Mision) : Double{
        return consumoBase
    }
}

class Transbordador(
    nombre: String,
    codigoDeIdentificacion: Int,
    fechaDeFabricacion: LocalDate,
    velocidadPromedio: Double,
    autonomia: Double,
    consumoBase: Double,
    seEncuentraEnMision: Boolean,
    baseAsignada: BaseDeLanzamiento,
    val capacidadTripulanes: Int,
) : NaveEspacial(nombre, codigoDeIdentificacion, fechaDeFabricacion, velocidadPromedio, autonomia, consumoBase, seEncuentraEnMision, baseAsignada) {

    override fun consumoAniosLuz(mision: Mision) : Double{
        val cantidadTripulantes = mision.tripulantes.size
        return consumoBase + (consumoBase * 0.1 * cantidadTripulantes)
    }
}

class Carguero(
    nombre: String,
    codigoDeIdentificacion: Int,
    fechaDeFabricacion: LocalDate,
    velocidadPromedio: Double,
    autonomia: Double,
    consumoBase: Double,
    seEncuentraEnMision: Boolean,
    baseAsignada: BaseDeLanzamiento,
    val capacidadCarga: Double,
) : NaveEspacial(nombre, codigoDeIdentificacion, fechaDeFabricacion, velocidadPromedio, autonomia, consumoBase, seEncuentraEnMision, baseAsignada) {

    override fun consumoAniosLuz(mision: Mision) : Double{
        val cantidadCarga = mision.carga
        var consumo = consumoBase + (consumoBase * 0.5 * cantidadCarga)

        if (antiguedad() > 10){
            consumo *= 1.20
        }
        return consumo
    }
}