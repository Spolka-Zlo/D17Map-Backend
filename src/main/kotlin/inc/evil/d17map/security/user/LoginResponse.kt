package inc.evil.d17map.security.user

import inc.evil.d17map.enums.Role

data class LoginResponse(
    val token: String,
    val role: Role
)
