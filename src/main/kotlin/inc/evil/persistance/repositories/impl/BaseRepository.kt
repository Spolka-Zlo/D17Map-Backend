package inc.evil.persistance.repositories.impl

import inc.evil.persistance.repositories.Repository

open class BaseRepository<T, ID> : Repository<T, ID> {
    override fun count() {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")

    }

    override fun findAll(): List<T> {
        TODO("Not yet implemented")
    }

    override fun <S : T> save(entity: S): S {
        TODO("Not yet implemented")
    }

    override fun findById(id: ID): T? {
        TODO("Not yet implemented")
    }

    override fun existsById(id: ID): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: ID) {
        TODO("Not yet implemented")
    }
}