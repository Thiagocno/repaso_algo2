/*
package ar.edu.grupo10

import ar.edu.unsam.algo2.repositorios.RepositorioPlaneta

class ActualizadorPlanetas(
    private val service: ServicePlanetas,
    private val repo: RepositorioPlaneta,
    private val parser: PlanetaParser = PlanetaParser()
) {

    fun actualizar() {
        val json = service.getPlanetas()
        val planetasDTO = parser.parse(json)

        planetasDTO.forEach { dto ->
            val planeta = dto.toModel()
            val id = dto.id

            if (id != null) {
                try {
                    repo.getById(id)
                    repo.update(planeta)
                } catch (e: Exception) {
                    repo.create(planeta)
                }
            } else {
                repo.create(planeta)
            }
        }
    }
}*/