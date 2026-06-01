package ar.edu.unsam.algo2.repositorios

import ar.edu.unsam.algo2.Subvencion

class RepositorioSubvencion : RepositorioMemoria<Subvencion>() {
    override fun search(value: String): List<Subvencion> {
        return elementos.filter { it.nombre.startsWith(value, ignoreCase = true) }
    }
}