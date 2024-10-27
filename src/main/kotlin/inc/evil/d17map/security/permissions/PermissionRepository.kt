package inc.evil.d17map.security.permissions

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PermissionRepository : JpaRepository<Permission, UUID> {
}