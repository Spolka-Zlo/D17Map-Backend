package inc.evil.dto

import inc.evil.config.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class EquipmentPostDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String,
)

@Serializable
data class EquipmentSummaryDto(
    val name: String
)