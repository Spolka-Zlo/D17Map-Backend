package inc.evil.d17map.security.authentication

import inc.evil.d17map.entities.Building
import inc.evil.d17map.entities.User
import inc.evil.d17map.exceptions.BuildingNotFoundException
import inc.evil.d17map.exceptions.RoleNotFoundException
import inc.evil.d17map.exceptions.UserAlreadyExistsException
import inc.evil.d17map.mappers.toRoleResponse
import inc.evil.d17map.repositories.BuildingRepository
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.UserPrincipal
import inc.evil.d17map.security.authentication.jwt.TokenProvider
import inc.evil.d17map.security.authorization.Role
import inc.evil.d17map.security.authorization.RoleRepository
import inc.evil.d17map.security.authorization.UserBuildingRole
import inc.evil.d17map.security.authorization.UserBuildingRoleRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val buildingRepository: BuildingRepository,
    private val userBuildingRoleRepository: UserBuildingRoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider
) {
    companion object {
        const val DEFAULT_ROLE = "ROLE_STUDENT"
    }

    fun registerUser(registerRequest: AuthRequest, buildingName: String) {
        logger.info { "Checking if user ${registerRequest.username} already exists." }
        validateUserDoesNotExist(registerRequest.username)


        val building = findBuilding(buildingName)
        val user = createUser(registerRequest)
        val role = findRole(DEFAULT_ROLE)

        assignRoleToUserInBuilding(user, building, role)

        logger.info { "Registration completed successfully." }
    }

    @Throws(AuthenticationException::class)
    fun verifyUser(loginRequest: AuthRequest, buildingName: String): AuthResponse {
        logger.info { "Checking user's ${loginRequest.username} credentials." }

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        val userPrincipal = authentication.principal as UserPrincipal

        buildingRepository.findByName(buildingName)
            ?: throw BuildingNotFoundException(buildingName)

        val userRoles =
            userBuildingRoleRepository.findAllByUserEmailAndBuildingName(loginRequest.username, buildingName)
                .map { SimpleGrantedAuthority(it.role.name) }
                .toMutableList()


        if (userRoles.isEmpty()) {
            userRoles.add(SimpleGrantedAuthority(DEFAULT_ROLE))
        }

        userPrincipal.authorities = userRoles

        logger.info { "Generating token..." }
        val token = tokenProvider.generateToken(authentication)
        val roles = toRoleResponse(userRoles)

        logger.info { "Token generated successfully" }
        logger.info { "Logged user's roles: $roles" }
        return AuthResponse(
            token = token,
            roles = roles
        )
    }


    private fun validateUserDoesNotExist(username: String) {
        if (userRepository.existsByEmail(username))
            throw UserAlreadyExistsException(username)
    }

    private fun createUser(registerRequest: AuthRequest): User {
        val user = User(
            email = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password)
        ).also { user ->
            logger.info { "User ${user.email} created with encoded password." }
        }

        return userRepository.save(user)
    }


    private fun findBuilding(buildingName: String): Building =
        buildingRepository.findByName(buildingName)
            ?: throw BuildingNotFoundException("")


    private fun findRole(roleName: String): Role =
        roleRepository.findByName(roleName)
            ?: throw RoleNotFoundException(roleName)


    private fun assignRoleToUserInBuilding(user: User, building: Building, role: Role) {
        val exists = userBuildingRoleRepository.existsByUserAndBuildingAndRole(user, building, role)
        if (exists) {
            logger.warn { "User ${user.email} already has role ${role.name} in building ${building.name}" }
            return
        }

        val userBuildingRole = UserBuildingRole(
            user = user,
            building = building,
            role = role
        )
        userBuildingRoleRepository.save(userBuildingRole)
        logger.info { "Assigned role ${role.name} to user ${user.email} in building ${building.name}" }
    }
}


