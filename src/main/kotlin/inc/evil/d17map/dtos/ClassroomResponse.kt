package inc.evil.d17map.dtos

import java.util.*

data class ClassroomResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val capacity: Int,
    val equipments: Set<EquipmentResponse>
)
