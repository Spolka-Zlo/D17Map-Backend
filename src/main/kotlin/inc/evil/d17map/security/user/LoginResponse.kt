package inc.evil.d17map.security.user

data class LoginResponse(
    val token: String,
    val roles: List<String>
)
