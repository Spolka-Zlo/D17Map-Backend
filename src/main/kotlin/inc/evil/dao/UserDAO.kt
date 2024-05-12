package inc.evil.dao

import inc.evil.entities.UserEntity
import inc.evil.tables.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class UserDAO {
    fun getUserById(userId: UUID): UserEntity? {
        return transaction {
            User.select { User.id eq userId }
                .map { UserEntity.fromResultRow(it) }
                .singleOrNull()
        }
    }

    fun createUser(user: UserEntity) {
        transaction {
            User.insert {
                it[id] = user.id
                it[password] = user.password
                it[email] = user.email
                it[role] = user.role
            }
        }
    }

    fun updateUser(user: UserEntity) {
        transaction {
            User.update({ User.id eq user.id }) {
                it[password] = user.password
                it[email] = user.email
                it[role] = user.role
            }
        }
    }

    fun deleteUser(userId: UUID) {
        transaction {
            User.deleteWhere { User.id eq userId }
        }
    }
}
