package inc.evil.d17map.dtos

import java.util.UUID

data class ClassroomSummary(
    val id: UUID,
    val name: String,
    val capacity: Int,
)
