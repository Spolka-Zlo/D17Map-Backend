package inc.evil.d17map.dtos

import inc.evil.d17map.enums.Role
import java.util.*

data class UserResponse(
    val id: UUID,
    val email: String,
    val userType: Role
)