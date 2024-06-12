package inc.evil.dto

import inc.evil.config.UUIDSerializer
import inc.evil.enums.UserType
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserSummaryDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val email: String
)

@Serializable
data class UserPostDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val email: String,
    val password: String,
    val role: UserType
)
