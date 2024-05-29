package inc.evil.dto

import inc.evil.plugins.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class ClassroomSummaryDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val capacity: Int,
    val equipment: List<String>
)

@Serializable
data class ClassroomPostDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val name: String,
    val description: String,
    val capacity: Int,
    val equipment: List<String>
)

@Serializable
data class ClassroomBasicInfoDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String
)