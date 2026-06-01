package ar.edu.unsam.algo2.repositorios

interface Repositorio<T : ID> {
    fun create(objeto: T)
    fun delete(objeto: T)
    fun update(objeto: T)
    fun getById(id: Int): T?
    fun search(value: String): List<T>
}