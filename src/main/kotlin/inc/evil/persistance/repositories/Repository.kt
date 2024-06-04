package inc.evil.persistance.repositories

/*
* Repository is an interface based on Spring's CrudRepository interface
* Commented out methods are left for future tasks
* */
interface Repository<ID, T> {
    fun count(): Long
    //fun delete(entity: T)
    fun deleteAll(): Unit
    //fun deleteAll(entities: Iterable<T>)
    //fun deleteAllById(ids: Iterable<ID>): Unit
    fun deleteById(id: ID): Unit
    fun existsById(id: ID): Boolean
    fun findAll(): List<T>
    //fun findAllById(ids: Iterable<ID>): List<T>
    fun findById(id: ID): T?
    fun <S : T> save(entity: S): S
    //fun <S :T > saveAll(entities: Iterable<S>): S

}