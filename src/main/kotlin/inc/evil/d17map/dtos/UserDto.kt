package inc.evil.d17map.dtos

import inc.evil.d17map.enums.Role
import java.util.*

data class UserDto(
    val id: UUID? = null,
    val email: String,
    val password: String? = null,
    val userType: Role
)
