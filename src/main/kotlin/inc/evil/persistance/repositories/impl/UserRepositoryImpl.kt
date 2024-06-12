package inc.evil.persistance.repositories.impl

import inc.evil.enums.UserType
import inc.evil.persistance.entities.User
import inc.evil.persistance.entities.Users
import inc.evil.persistance.repositories.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserRepositoryImpl : BaseRepository<UUID, User>(User), UserRepository {
    override fun findByEmail(email: String): User? = transaction {
        User.find { Users.email eq email }.singleOrNull()
    }

    override fun findByRole(role: UserType): List<User> = transaction {
        User.find { Users.role eq role }.toList()
    }
}
