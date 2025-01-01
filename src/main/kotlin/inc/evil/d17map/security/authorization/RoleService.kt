package inc.evil.d17map.security.authorization

import inc.evil.d17map.mappers.toRoleResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun hasAccessToClass(classId: UUID) {

        logger.info { "HELLOOOOOOOOOOOOOOOOOO" }

        val roles = SecurityContextHolder.getContext().authentication.authorities
        val roleNames = toRoleResponse(roles)
        roleRepository.findRolesWithClassroomAccess(roleNames, classId).isEmpty()
    }


}