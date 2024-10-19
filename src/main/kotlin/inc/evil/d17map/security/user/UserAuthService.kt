package inc.evil.d17map.security.user

import inc.evil.d17map.entities.User
import inc.evil.d17map.enums.Role
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.jwt.JWTService


import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JWTService
) {

    // TODO user reservations during registration seem to be awkward :/
    fun registerUser(registerRequest: AuthRequest): User {
        val user = User(
            email = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password),
            reservations = mutableSetOf(),
            userType = Role.STUDENT,
        )
        return userRepository.save(user)
    }

    fun verify(loginRequest: AuthRequest): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        return if (authentication.isAuthenticated) {
            jwtService.generateToken(loginRequest.username)
        } else {
            "Fail" // TODO throw exception xD
        }
    }
}

