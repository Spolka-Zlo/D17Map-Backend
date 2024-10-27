package inc.evil.d17map.security.user

import inc.evil.d17map.entities.User
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.jwt.TokenProvider
import inc.evil.d17map.security.roles.RoleRepository


import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider
) {

    fun registerUser(registerRequest: AuthRequest) {

        // TODO when adding new exceptions - change Exception to custom one. Then while catching it reply with 409 status
        if (userRepository.existsByEmail(registerRequest.username)) throw Exception("User with email ${registerRequest.username} already exists")

        roleRepository.findByName("ROLE_STUDENT")?.let {
            val user = User(
                email = registerRequest.username,
                password = passwordEncoder.encode(registerRequest.password),
                roles = mutableSetOf(it),
            )
            userRepository.save(user)
        } ?: throw Exception("ROLE_STUDENT not found")

    }

    @Throws(AuthenticationException::class)
    fun verifyUser(loginRequest: AuthRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        return LoginResponse(
            token = tokenProvider.generateToken(authentication),
            roles = authentication.authorities.map { it.authority },
        )
    }
}

