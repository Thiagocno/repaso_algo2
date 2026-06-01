package ar.edu.unsam.algo2.repositorios

import ar.edu.unsam.algo2.NaveEspacial
import kotlin.text.equals

class RepositorioNave: RepositorioMemoria <NaveEspacial>() {
    override fun search(value: String): List<NaveEspacial> {
        return elementos.filter { it.codigoDeIdentificacion.equals(value, ignoreCase = true) }
    }
}