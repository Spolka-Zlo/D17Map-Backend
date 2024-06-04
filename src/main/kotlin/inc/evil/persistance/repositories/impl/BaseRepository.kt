package inc.evil.persistance.repositories.impl

import inc.evil.persistance.repositories.Repository
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.transactions.transaction

open class BaseRepository<ID : Comparable<ID>, T : Entity<ID>>(private val entityClass: EntityClass<ID, T>) :
    Repository<ID, T> {

    override fun count(): Long {
        return transaction {
            entityClass.all().count()
        }
    }

    override fun deleteAll() {
        transaction {
            entityClass.all().forEach { it.delete() }
        }
    }

    override fun findAll(): List<T> {
        return transaction {
            entityClass.all().toList()
        }
    }

    override fun <S : T> save(entity: S): S {
        return transaction {
            entity.apply {
                this.flush()
            }
        }
    }

    override fun findById(id: ID): T? {
        return transaction {
            entityClass.findById(id)
        }
    }

    override fun existsById(id: ID): Boolean {
        return transaction {
            entityClass.findById(id) != null
        }
    }

    override fun deleteById(id: ID) {
        transaction {
            entityClass.findById(id)?.delete()
        }
    }
}
