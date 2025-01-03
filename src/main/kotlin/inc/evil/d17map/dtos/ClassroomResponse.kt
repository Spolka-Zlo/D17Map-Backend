package inc.evil.d17map.dtos

import java.util.*

data class ClassroomResponse(
    val name: String,
    val description: String,
    val modelKey: String,
    val capacity: Int,
    val equipmentIds: Set<UUID>,
    val id: UUID,
    val floorName: String,
)
