package inc.evil.dto

import inc.evil.config.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserSummaryDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val email: String
)
