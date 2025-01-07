package inc.evil.d17map.services

import inc.evil.d17map.dtos.users.UserRequest
import inc.evil.d17map.dtos.users.UserResponse
import inc.evil.d17map.entities.User
import inc.evil.d17map.exceptions.UserAlreadyExistsException
import inc.evil.d17map.exceptions.UserNotFoundException
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.authorization.Role
import inc.evil.d17map.security.authorization.RoleService
import inc.evil.d17map.security.authorization.UserBuildingRoleRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val buildingService: BuildingService,
    private val roleService: RoleService,
    private val userBuildingRoleRepository: UserBuildingRoleRepository
) {

    @Transactional
    fun createUser(userRequest: UserRequest, buildingName: String): UserResponse {
        val user = createAndSaveUser(userRequest)
        val building = buildingService.findBuilding(buildingName)

        val roles = roleService.assignRolesToUserInBuilding(user, building, userRequest.roles)
        logger.info { "User creation completed successfully." }

        return buildUserResponse(user, roles)
    }

    @Transactional
    fun updateUser(userRequest: UserRequest, buildingName: String): UserResponse {
        val user = userRepository.findByEmail(userRequest.username) ?: throw UserNotFoundException(userRequest.username)
        val building = buildingService.findBuilding(buildingName)

        if (userRequest.roles.isNullOrEmpty()) throw RuntimeException("Roles...")

        val roles = roleService.updateAssignedRoles(user, building, userRequest.roles)
        logger.info { "User update completed successfully." }

        return buildUserResponse(user, roles)
    }

    private fun createAndSaveUser(userRequest: UserRequest): User {
        if (userRepository.existsByEmail(userRequest.username)) throw UserAlreadyExistsException(userRequest.username)
        val user = User(
            email = userRequest.username,
            password = passwordEncoder.encode(userRequest.password)
        ).also { logger.info { "User ${it.email} created with encoded password." } }

        return userRepository.save(user)
    }

    private fun buildUserResponse(user: User, roles: List<Role>) =
        UserResponse(user.id!!, user.email, roles.map { it.name })

}