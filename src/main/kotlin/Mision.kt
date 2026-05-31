package ar.edu.unsam.algo2

import java.time.LocalDate

enum class EstadoMision {
    BORRADOR,
    EN_CURSO,
    COMPLETADA,
    FALLIDA,
    CANCELADA
}

class Mision (
    val nombre: String,
    val descripcion: String,
    val fechaDeLanzamiento: LocalDate,
    val naveAsignada: NaveEspacial,
    val tripulantes: MutableList<Tripulante> = mutableListOf(),
    val planetaDestino: Planeta,
    var carga: Double,
){

    public fun esValido() : Boolean{
        if (nombre.isBlank()) {
            throw Exception("El nombre no puede estar vacio")
        }
        if (naveAsignada == null) {
            throw Exception("Debe haber una nave asignada")
        }
        if (planetaDestino == null) {
            throw Exception("Debe haber un planeta destino")
        }
        if (fechaDeLanzamiento >= LocalDate.now()) {
            throw Exception("La fecha de lanzamiento debe ser posterior a hoy")
        }
        return true
    }

    var estadoMision = EstadoMision.BORRADOR

    fun duracionEstimada() : Double{
        return ((planetaDestino.distanciaATierra * 365) / naveAsignada.velocidadPromedio) * 2
    }

    fun esDeAltoRiesgo() : Boolean = !planetaDestino.esHabitable() && duracionEstimada() > 500

    fun agregarTripulantes(tripulante: Tripulante){
        if (estadoMision != EstadoMision.BORRADOR) {
            throw Exception("Solo se pueden agregar tripulantes en estado BORRADOR")
        }
        tripulantes.add(tripulante)
    }

    fun lanzarMision(){
        if (estadoMision != EstadoMision.BORRADOR){
            throw Exception("La mision debe estar en BORRADOR")
        }

        if (!naveAsignada.puedeAlcanzarPlaneta(planetaDestino)){
            throw Exception("Planeta inalcanzable por nave")
        }

        if (naveAsignada is Sonda && tripulantes.isNotEmpty()){
            throw Exception("Las sondas no pueden llevar tripulantes")
        }

        if (!(naveAsignada is Sonda)){

            if (tripulantes.all   { it.esValido() }){
                throw Exception("Algun(os) tripulante(s) invalido(s)")
            }

            if (naveAsignada is Transbordador){
                if(tripulantes.size > naveAsignada.capacidadTripulanes){
                    throw Exception("La cantidad de tripulantes supera la capacidad de la nave")
                }
            }

            val base = tripulantes.first().baseAsignada

            if (tripulantes.any{it.baseAsignada != base}){
                throw Exception("Todos los tripulantes deben estar en la misma base")
            }

            if (naveAsignada.baseAsignada != base){
                throw Exception("La nave debe estar en la misma base que los tripulantes")
            }
        }

        naveAsignada.seEncuentraEnMision = true

        estadoMision = EstadoMision.EN_CURSO

    }

    fun completar(){
        if (estadoMision != EstadoMision.EN_CURSO) {
            throw Exception("La misión no está en curso")
        }

        estadoMision = EstadoMision.COMPLETADA

        planetaDestino.fueAterrizado = true

        tripulantes.forEach { it.misionesExitosas ++ }

        liberarNave()
    }

    fun fallar(){
        if (estadoMision != EstadoMision.EN_CURSO) {
            throw Exception("La misión no está en curso")
        }

        estadoMision = EstadoMision.FALLIDA

        tripulantes.forEach { it.misionesFracasadas ++ }

        liberarNave()
    }

    fun cancelar(){
        if (estadoMision != EstadoMision.EN_CURSO) {
            throw Exception("La misión no está en curso")
        }

        if (esDeAltoRiesgo()){
            tripulantes.forEach { it.misionesParciales ++ }
        }

        estadoMision = EstadoMision.CANCELADA

        liberarNave()
    }

    private fun liberarNave() {
        naveAsignada.seEncuentraEnMision = false
    }
}