package inc.evil.d17map.security.authentication

import inc.evil.d17map.entities.User
import inc.evil.d17map.exceptions.RoleNotFoundException
import inc.evil.d17map.exceptions.UserAlreadyExistsException
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.authentication.jwt.TokenProvider
import inc.evil.d17map.security.authorization.RoleRepository


import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: TokenProvider
) {
    companion object {
        const val DEFAULT_ROLE = "ROLE_STUDENT"
    }

    fun registerUser(registerRequest: AuthRequest) {
        if (userRepository.existsByEmail(registerRequest.username))
            throw UserAlreadyExistsException(registerRequest.username)

        roleRepository.findByName(DEFAULT_ROLE)?.let {
            val user = User(
                email = registerRequest.username,
                password = passwordEncoder.encode(registerRequest.password),
                roles = mutableSetOf(it),
            )
            userRepository.save(user)
        } ?: throw RoleNotFoundException(DEFAULT_ROLE)
    }

    @Throws(AuthenticationException::class)
    fun verifyUser(loginRequest: AuthRequest): PositiveAuthResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        return PositiveAuthResponse(
            token = tokenProvider.generateToken(authentication),
            roles = authentication.authorities.map { it.authority },
        )
    }
}

