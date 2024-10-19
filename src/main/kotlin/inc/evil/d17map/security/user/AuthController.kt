package inc.evil.d17map.security.user


import inc.evil.d17map.entities.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
@SecurityRequirements // WARNING: Hacky way of disabling authorization for this controller in OpenApi spec
class AuthController(
    private val userAuthService: UserAuthService
) {

    @Operation(
        summary = "Create a new user",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(
                examples = [ExampleObject(value = """{"username": "radek", "password": "123456"}""")]
            )]
        )
    )
    @PostMapping("/register")
    fun register(@RequestBody authRequest: AuthRequest): User {
        return userAuthService.registerUser(authRequest)
    }

    @Operation(
        summary = "Authenticate a user and return JWT",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(
                examples = [ExampleObject(value = """{"username": "radek", "password": "123456"}""")]
            )]
        )
    )
    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): String {
        return userAuthService.verify(authRequest)
    }
}
