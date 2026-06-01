package ar.edu.unsam.algo2.repositorios

import ar.edu.unsam.algo2.BaseDeLanzamiento


class RepositorioBaseDeLanzamiento : RepositorioMemoria <BaseDeLanzamiento>() {
    override fun search(value: String): List<BaseDeLanzamiento> {
        return elementos.filter {
            it.nombre.contains(value, ignoreCase = true) ||
                    it.direccion.pais.equals(value, ignoreCase = true)
        }
    }
}