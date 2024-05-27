package inc.evil.dto

import inc.evil.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ClassroomFullDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val description: String,
    val details: ClassroomDetailsDto
)

@Serializable
data class ClassroomSummaryDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String
)

@Serializable
data class ClassroomDetailsDto(
    val numberOfSeats: Int,
    val equipment: List<String>
)