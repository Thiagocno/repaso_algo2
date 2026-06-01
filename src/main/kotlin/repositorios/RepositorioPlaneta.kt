package ar.edu.unsam.algo2.repositorios

import ar.edu.unsam.algo2.Planeta


class RepositorioPlaneta : RepositorioMemoria <Planeta>() {
    override fun search(value: String): List<Planeta> {
        return elementos.filter {
            it.nombre.contains(value, ignoreCase = true)
        }
    }
}