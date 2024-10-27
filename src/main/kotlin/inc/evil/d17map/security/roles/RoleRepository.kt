package inc.evil.d17map.security.roles


import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Role?
}