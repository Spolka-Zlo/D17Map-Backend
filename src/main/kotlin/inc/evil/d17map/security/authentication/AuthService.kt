package inc.evil.d17map.security.authentication

import inc.evil.d17map.dtos.users.UserRequest
import inc.evil.d17map.security.authentication.jwt.TokenProvider
import inc.evil.d17map.services.BuildingService
import inc.evil.d17map.services.UserService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}

@Service
class AuthService(
    private val buildingService: BuildingService,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider,
    private val userService: UserService
) {
    fun registerUser(registerRequest: AuthRequest, buildingName: String) {
        val userRequest = UserRequest(registerRequest.username, registerRequest.password)
        userService.createUser(userRequest, buildingName)
    }

    @Throws(AuthenticationException::class)
    fun verifyUser(loginRequest: AuthRequest, buildingName: String): AuthResponse {
        logger.info { "Checking user's ${loginRequest.username} credentials." }

        buildingService.findBuilding(buildingName)

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        logger.info { "Generating token..." }
        val token = tokenProvider.generateToken(authentication, buildingName)
        val roles = tokenProvider.getRoles(authentication.name, buildingName)

        logger.info { "Token generated successfully" }
        logger.info { "Logged user's roles: $roles" }
        return AuthResponse(
            token = token,
            roles = roles.map { it.name }
        )
    }
}


