package inc.evil.persistance.repositories.impl

import inc.evil.persistance.repositories.Repository

abstract class InMemoryRepository<ID,  T>: Repository<ID, T> {

    private val memory = HashMap<ID, T>()

    override fun count(): Long {
        return memory.count().toLong()
    }

    override fun deleteAll() {
        memory.clear()
    }

    override fun findAll(): List<T> {
        return memory.values.toList()
    }

    override fun <S : T> save(entity: S): S {
        TODO("not implemented")
    }

    override fun findById(id: ID): T? {
        return memory[id]
    }

    override fun existsById(id: ID): Boolean {
        return id in memory
    }

    override fun deleteById(id: ID) {
        memory.remove(id)
    }
}

