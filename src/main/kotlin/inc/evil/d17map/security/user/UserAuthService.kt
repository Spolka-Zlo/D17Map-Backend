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

    fun registerUser(registerRequest: AuthRequest) {

        // TODO when adding new exceptions - change Exception to custom one. Then while catching it reply with 409 status
        if(userRepository.existsByEmail(registerRequest.username)) throw Exception("User with email ${registerRequest.username} already exists")

        val user = User(
            email = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password),
            userType = Role.STUDENT,
        )
        userRepository.save(user)
    }

    fun verifyUser(loginRequest: AuthRequest): LoginResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        return LoginResponse(
            token = jwtService.generateToken(loginRequest.username),
            role = Role.STUDENT,
        )
    }
}

