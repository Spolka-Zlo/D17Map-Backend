package inc.evil.persistance.repositories.impl

import inc.evil.persistance.repositories.Repository
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass

open class BaseRepository<ID : Comparable<ID>, T : Entity<ID>>(private val entityClass: EntityClass<ID, T>) :
    Repository<ID, T> {
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