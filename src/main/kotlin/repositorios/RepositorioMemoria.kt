package ar.edu.unsam.algo2.repositorios

abstract class RepositorioMemoria <T : ID> : Repositorio<T> {

    protected val elementos = mutableListOf<T>()
    private var proximoId = 1

    override fun create(objeto: T) {
        objeto.id = proximoId
        proximoId++
        elementos.add(objeto)
    }

    override fun delete(objeto: T) {
        elementos.removeIf { it.id == objeto.id }
    }

    override fun update(objeto: T) {
        val indice = elementos.indexOfFirst { it.id == objeto.id }

        if (indice == -1) {
            throw Exception("no existe un objeto con id ${objeto.id}")
        }

        elementos[indice] = objeto
    }

    override fun getById(id: Int): T? {
        return elementos.find { it.id == id }

    }

    abstract override fun search(value: String): List<T>
}