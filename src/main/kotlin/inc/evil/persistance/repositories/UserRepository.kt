package inc.evil.persistance.repositories

import inc.evil.enums.UserType
import inc.evil.persistance.entities.User
import java.util.UUID

interface UserRepository : Repository<UUID, User> {
    fun findByEmail(email: String): User?
    fun findByRole(role: UserType): List<User>
}
