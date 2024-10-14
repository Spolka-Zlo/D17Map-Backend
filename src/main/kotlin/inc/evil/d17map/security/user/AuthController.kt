package inc.evil.d17map.security.user


import inc.evil.d17map.entities.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class UserController(
    private val userAuthService: UserAuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): User {
        return userAuthService.registerUser(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User): String {
        return userAuthService.verify(user)
    }
}
