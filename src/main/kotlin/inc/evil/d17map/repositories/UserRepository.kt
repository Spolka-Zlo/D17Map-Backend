package inc.evil.d17map.repositories

import inc.evil.d17map.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID>{
    fun findByEmail(username: String): User?
    fun existsByEmail(email: String): Boolean
}