package inc.evil.d17map.security.authentication

import inc.evil.d17map.entities.User
import inc.evil.d17map.exceptions.RoleNotFoundException
import inc.evil.d17map.exceptions.UserAlreadyExistsException
import inc.evil.d17map.mappers.toRoleResponse
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.authentication.jwt.TokenProvider
import inc.evil.d17map.security.authorization.RoleRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider,

    ) {
    companion object {
        const val DEFAULT_ROLE = "ROLE_STUDENT"
    }

    fun registerUser(registerRequest: AuthRequest) {
        logger.info { "Checking if user ${registerRequest.username} already exists." }
        if (userRepository.existsByEmail(registerRequest.username))
            throw UserAlreadyExistsException(registerRequest.username)

        logger.info { "Registering user: ${registerRequest.username} with role $DEFAULT_ROLE" }
        roleRepository.findByName(DEFAULT_ROLE)?.let {
            val user = User(
                email = registerRequest.username,
                password = passwordEncoder.encode(registerRequest.password),
                roles = mutableSetOf(it),
            )
            userRepository.save(user)
        } ?: throw RoleNotFoundException(DEFAULT_ROLE)

        logger.info { "Registration completed successfully." }
    }

    @Throws(AuthenticationException::class)
    fun verifyUser(loginRequest: AuthRequest): AuthResponse {
        logger.info { "Checking user's ${loginRequest.username} credentials." }
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        logger.info { "Generating token..." }
        val token = tokenProvider.generateToken(authentication)
        val roles = toRoleResponse(authentication.authorities)

        logger.info { "Token generated successfully" }
        logger.info { "Logged user's roles: $roles" }
        return AuthResponse(
            token = token,
            roles = roles
        )
    }
}

