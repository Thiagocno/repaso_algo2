package ar.edu.unsam.algo2.repositorios

import ar.edu.unsam.algo2.Mision

class RepositorioMision : RepositorioMemoria <Mision>() {
    override fun search(value: String): List<Mision> {
        return elementos.filter {
            it.nombre.equals(value, ignoreCase = true) ||
                    it.planetaDestino.nombre.equals(value, ignoreCase = true) }
    }
}