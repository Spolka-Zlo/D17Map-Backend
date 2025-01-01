package inc.evil.d17map.security.authorization


import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Role?
    fun existsByName(name: String): Boolean
}