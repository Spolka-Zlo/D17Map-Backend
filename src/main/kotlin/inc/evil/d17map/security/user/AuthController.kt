package inc.evil.d17map.security.user


import inc.evil.d17map.entities.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userAuthService: UserAuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody authRequest: AuthRequest): User {
        return userAuthService.registerUser(authRequest)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): String {
        return userAuthService.verify(authRequest)
    }
}
