package inc.evil.dto

import inc.evil.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserSummaryDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val email: String
)
