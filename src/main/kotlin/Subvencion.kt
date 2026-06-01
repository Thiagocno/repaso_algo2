package ar.edu.unsam.algo2

import ar.edu.unsam.algo2.repositorios.ID
import java.time.LocalDate

interface TipoDeSubvencion{
    fun montoAgregado(mision: Mision, subvencion: Subvencion): Double

    fun condicionTipo(mision: Mision): Boolean
}

class SubvencionGubernamental() : TipoDeSubvencion{
    override fun condicionTipo(mision: Mision): Boolean {
        return !mision.planetaDestino.fueAterrizado
    }

    override fun montoAgregado(mision: Mision, subvencion: Subvencion): Double {
        if (mision.planetaDestino.esHabitable()){
            return subvencion.montoBase * 0.3
        }
        return subvencion.montoBase * 0.15
    }
}

class SubvencionPrivada() : TipoDeSubvencion{
    override fun condicionTipo(mision: Mision): Boolean {
        return mision.tripulantes.map { it.experiencia() }.average() > 5
    }

    override fun montoAgregado(mision: Mision, subvencion: Subvencion): Double {
        val montoAdicional = mision.tripulantes.count { it.experiencia() > 10 } * 10000.0

        if (montoAdicional > 50000) {
            return 50000.0
        }
        return montoAdicional
    }
}

class SubvencionDeEmergencia    () : TipoDeSubvencion{
    override fun condicionTipo(mision: Mision): Boolean {
        return true
    }

    override fun montoAgregado(mision: Mision, subvencion: Subvencion): Double {
        return mision.presupuestoFijo() * 0.5
    }
}

class Subvencion (
    val fechaDeEmision: LocalDate,
    val duracion: Int,
    val montoBase: Double,
    val nombre: String,
    var estaAplicada: Boolean = false,
    var tipoDeSubvencion: TipoDeSubvencion,

) : ID{

    override var id: Int = 0

    fun estaVencida(): Boolean {
        val fechaVencimiento = fechaDeEmision.plusDays(duracion.toLong())
        return LocalDate.now().isEqual(fechaVencimiento)
    }

    fun esAplicableParaMision(mision: Mision): Boolean {
        return !estaVencida() &&
                !estaAplicada &&
                montoBase<=mision.presupuestoFijo()
    }

    fun montoAdicional(mision: Mision) : Double{
        if(tipoDeSubvencion.condicionTipo(mision)){
            return tipoDeSubvencion.montoAgregado(mision, this)
        }
        return 0.0
    }

    fun montoTotal(mision: Mision) : Double {
        return montoBase + montoAdicional(mision)
    }
}