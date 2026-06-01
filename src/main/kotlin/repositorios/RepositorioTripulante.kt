package ar.edu.unsam.algo2.repositorios

import ar.edu.unsam.algo2.Tripulante

class RepositorioTripulante : RepositorioMemoria <Tripulante>(){
    override fun search(value: String): List<Tripulante> {
        return elementos.filter {
            it.nombre.equals(value, ignoreCase = true) ||
                    it.apellido.equals(value, ignoreCase = true) }
    }
}
