package ar.edu.unsam.algo2

import org.uqbar.geodds.Point

class BaseDeLanzamiento(
    val nombre: String,
    val direccion: Direccion,
    var navesEstacionadas: List<NaveEspacial>,
    val capacidadMaximaDeNaves: Int,
) {
}

data class Direccion(
    val pais: String,
    val ciudad: String,
    val calle: String,
    val altura: Int,
    val ubicacionGeografica: Point,
)