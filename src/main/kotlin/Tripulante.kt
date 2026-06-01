package ar.edu.unsam.algo2

import ar.edu.unsam.algo2.repositorios.ID
import org.uqbar.geodds.Point
import java.awt.geom.Point2D.distance
import java.time.LocalDate
import java.time.Period

interface PerfilAptitud{
    fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean
}

class PerfilConformista : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean = true
}

class PerfilPrudente : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        return mision.planetaDestino.tieneTemperaturaIdeal() && mision.planetaDestino.tieneGravedadSoportable()
    }
}

class PerfilVeterano (val maximoDias : Int) : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        return mision.duracionEstimada() <= maximoDias
    }
}

class PerfilCauteloso (val umbralRadiacion : Int) : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        return mision.planetaDestino.nivelDeRadiacion < umbralRadiacion
    }
}

class PerfilExigenteConNave : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        return mision.naveAsignada.esModerna()
    }
}

class PerfilExplorador : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        return !mision.planetaDestino.fueAterrizado
    }
}

class PerfilTemerarios : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        return mision.planetaDestino.esHabitable() || mision.esDeAltoRiesgo()
    }
}

class PerfilSegunEdad : PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision) : Boolean {
        if (tripulante.edadActual() % 2 == 0){
            return PerfilTemerarios().restricciones(tripulante, mision)
        }
        return PerfilPrudente().restricciones(tripulante, mision)
    }
}

class PerfilCompuestoY (private val perfiles: List<PerfilAptitud>): PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision): Boolean {
        return perfiles.all{ it.restricciones(tripulante, mision) }
    }
}

class PerfilCompuestoO (private val perfiles: List<PerfilAptitud>): PerfilAptitud {
    override fun restricciones(tripulante: Tripulante, mision: Mision): Boolean {
        return perfiles.any{ it.restricciones(tripulante, mision) }
    }
}

interface Rol{
    fun bonusSalario(tripulante: Tripulante) : Double
}

class Comandante : Rol{
    override fun bonusSalario(tripulante: Tripulante) : Double {
        return (tripulante.salarioBase * 0.5) + (tripulante.salarioBase * 0.05 * tripulante.misionesExitosas)
    }
}

class Piloto : Rol{
    override fun bonusSalario(tripulante: Tripulante) : Double {
        return tripulante.salarioBase * 0.3
    }
}

class Ingeniero : Rol{
    override fun bonusSalario(tripulante: Tripulante) : Double {
        val ultimaMision = tripulante.ultimaMisionCompletada()
        if (ultimaMision?.naveAsignada is Carguero){
            return tripulante.salarioBase * 0.4
        }
        return tripulante.salarioBase * 0.2
    }
}

class Cientifico : Rol{
    override fun bonusSalario(tripulante: Tripulante) : Double {
        val cantidadPlanetasAterrizados = tripulante.historialMisiones.count{
            it.planetaDestino.fueAterrizado
        }
        return tripulante.salarioBase * 0.1 * cantidadPlanetasAterrizados
    }
}

class Medico : Rol{
    override fun bonusSalario(tripulante: Tripulante) : Double {
        return tripulante.salarioBase * 0.25 + (tripulante.misionesFracasadas * 0.02)
    }
}

class Tripulante (
    val nombre : String,
    val apellido : String,
    val fechaDeNacimiento : LocalDate,
    var misionesExitosas : Int,
    var misionesParciales : Int,
    var misionesFracasadas : Int,
    val fechaInicioActividad : LocalDate,
    val baseAsignada : BaseDeLanzamiento?,
    val salarioBase: Double,
    var estaEnMision: Boolean,
    var rol: Rol?,
    var perfilAptitud: PerfilAptitud,
    var historialMisiones: MutableList<Mision> = mutableListOf(),
    val kilometrosCercanos: Double,
    val ubicacionGeografica: Point,
) : ID {
    override var id: Int = 0

    public fun esValido() : Boolean{
        if (nombre.isBlank()) {
            throw Exception("El nombre no puede estar vacio")
        }
        if (fechaDeNacimiento >= LocalDate.now()) {
            throw Exception("La fecha de nacimiento debe ser anterior a hoy")
        }
        if (rol == null) {
            throw Exception("Debe tener un rol asignado")
        }
        if (baseAsignada == null) {
            throw Exception("Debe tener una base asignada")
        }
        return true
    }

    fun edadActual() : Int {
        return Period.between(fechaDeNacimiento, LocalDate.now()).years
    }

    fun aniosActivo() : Int {
        return LocalDate.now().year - fechaInicioActividad.year
    }

    fun experiencia() : Double {
        return aniosActivo().toDouble() +
                (misionesExitosas / 2.0) +
                (misionesParciales / 4.0) +
                (misionesFracasadas / 2.0)
    }

    fun cumpleCondicionesBase() : Boolean = experiencia() >= 3 && !estaEnMision

    fun esAptoParaMision(mision: Mision) : Boolean = cumpleCondicionesBase() && perfilAptitud.restricciones(tripulante = this, mision = mision)

    fun ultimaMisionCompletada() : Mision? {
        return historialMisiones.lastOrNull {
            it.estadoMision == EstadoMision.COMPLETADA
        }
    }

    fun salarioTotal() : Double {
        return salarioBase + (rol?.bonusSalario( tripulante = this) ?: 0.0)
    }

    fun cambiarPerfil(nuevoPerfil: PerfilAptitud) {
        perfilAptitud = nuevoPerfil
    }

    fun cambiarCalculadorBonus(nuevoRol: Rol) {
        rol = nuevoRol
    }

    fun esBaseCercana(base: BaseDeLanzamiento): Boolean {
        val distanciaABase = distance(
            ubicacionGeografica.x,
            ubicacionGeografica.y,
            base.direccion.ubicacionGeografica.x,
            base.direccion.ubicacionGeografica.y,
        )
        return distanciaABase <= kilometrosCercanos
    }
}