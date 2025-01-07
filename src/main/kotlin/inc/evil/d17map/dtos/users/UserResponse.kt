package inc.evil.d17map.dtos.users

import java.util.*

data class UserResponse(val id: UUID, val username: String, val roles: List<String>)