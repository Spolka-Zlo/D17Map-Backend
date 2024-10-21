package inc.evil.d17map.dtos

import java.util.*

data class ClassroomRequest(
    val name: String,
    val description: String,
    val capacity: Int,
    val equipmentIds: Set<UUID>
)
