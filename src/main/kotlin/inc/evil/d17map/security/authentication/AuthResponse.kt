package inc.evil.d17map.security.authentication

data class AuthResponse(
    val token: String,
    val roles: List<String>
)
