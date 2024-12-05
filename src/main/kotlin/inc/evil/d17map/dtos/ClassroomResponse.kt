package inc.evil.d17map.dtos

import inc.evil.d17map.entities.Floor
import java.util.*

data class ClassroomResponse(
    val name: String,
    val description: String,
    val modelKey: String,
    val capacity: Int,
    val equipmentIds: Set<UUID>,
    val id: UUID,
    val floor: Floor
)
