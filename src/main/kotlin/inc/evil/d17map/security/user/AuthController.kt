package inc.evil.d17map.security.user


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
@SecurityRequirements // WARNING: Hacky way of disabling authorization for this controller in OpenApi spec
class AuthController(
    private val userAuthService: UserAuthService
) {

    @Operation(
        summary = "Create a new user",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created a new user."),
            ApiResponse(responseCode = "400", description = "Invalid user data provided."),
            ApiResponse(responseCode = "409", description = "User already exists with the given username."),
        ]
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody authRequest: AuthRequest) = userAuthService.registerUser(authRequest)

    @Operation(
        summary = "Authenticate a user and return JWT",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully authenticated user and returned JWT."),
            ApiResponse(responseCode = "401", description = "Invalid username or password."),
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): LoginResponse {
        return userAuthService.verifyUser(authRequest)
    }
}
