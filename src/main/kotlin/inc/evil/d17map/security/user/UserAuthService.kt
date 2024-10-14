package inc.evil.d17map.security.user

import inc.evil.d17map.entities.User
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

    fun registerUser(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun verify(user: User): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user.email, user.password)
        )

        return if (authentication.isAuthenticated) {
            jwtService.generateToken(user.email)
        } else {
            "Fail"
        }
    }
}
