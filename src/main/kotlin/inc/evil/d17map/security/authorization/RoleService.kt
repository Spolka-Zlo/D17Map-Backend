package inc.evil.d17map.security.authorization

import inc.evil.d17map.entities.Building
import inc.evil.d17map.entities.User
import inc.evil.d17map.exceptions.MultipleRolesNotFoundException
import inc.evil.d17map.exceptions.RoleNotFoundException
import inc.evil.d17map.repositories.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val userBuildingRoleRepository: UserBuildingRoleRepository,
    private val userRepository: UserRepository
) {
    companion object {
        const val DEFAULT_ROLE = "ROLE_STUDENT"
    }

    fun hasAccessToClass(classId: UUID): Boolean {

        val roles = SecurityContextHolder.getContext().authentication.authorities.map { it.authority }
        logger.info { "Roles: $roles" }
        val l = roleRepository.findRolesWithClassroomAccess(roles, classId).isNotEmpty()
        logger.info { "Authorization result: $l" }

        return l

    }


    fun assignRolesToUserInBuilding(user: User, building: Building, roles: List<String>?): List<Role> {
        if (roles.isNullOrEmpty()) {
            return listOf(assignDefaultRoleToUserInBuilding(user, building)).map { it.role }
        }

        val existingRoles = roleRepository.findAllByNameIn(roles)
        val missingRoles = roles.filterNot { it in existingRoles.map { existingRole -> existingRole.name } }

        if (missingRoles.isNotEmpty()) throw MultipleRolesNotFoundException(missingRoles)
        return existingRoles.map { createAndSaveRoleAssignment(user, building, it).role }
    }


    fun updateAssignedRoles(user: User, building: Building, roles: List<String>): List<Role> {
        val existingRoles = roleRepository.findAllByNameIn(roles)
        val missingRoles = roles.filterNot { it in existingRoles.map { existingRole -> existingRole.name } }

        if (missingRoles.isNotEmpty()) throw MultipleRolesNotFoundException(missingRoles)


        val updatedUserBuildingRoles = existingRoles.map {
            UserBuildingRole(
                user = user,
                building = building,
                role = it
            )
        }.toMutableSet()

        user.usersBuildingsRoles = updatedUserBuildingRoles
        userRepository.save(user)

        return updatedUserBuildingRoles.map { it.role }
    }


    private fun createAndSaveRoleAssignment(user: User, building: Building, role: Role): UserBuildingRole {
        return userBuildingRoleRepository.findByUserAndBuildingAndRole(user, building, role)
            ?.also {
                logger.warn { "User ${user.email} already has role ${role.name} in building ${building.name}" }
            }
            ?: UserBuildingRole(
                user = user,
                building = building,
                role = role
            ).also {
                logger.info { "Assigned role ${role.name} to user ${user.email} in building ${building.name}" }
            }.let { userBuildingRoleRepository.save(it) }
    }


    private fun assignDefaultRoleToUserInBuilding(user: User, building: Building): UserBuildingRole {
        val defaultRole = roleRepository.findByName(DEFAULT_ROLE) ?: throw RoleNotFoundException(DEFAULT_ROLE)
        return createAndSaveRoleAssignment(user, building, defaultRole)
    }


}