package inc.evil.d17map.security.authentication

data class PositiveAuthResponse(
    val token: String,
    val roles: List<String>
)
