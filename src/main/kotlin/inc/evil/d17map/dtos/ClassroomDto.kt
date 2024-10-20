package inc.evil.d17map.dtos

import java.util.*

data class ClassroomDto(
    val name: String,
    val description: String,
    val capacity: Int,
    val equipmentIds: Set<UUID>? = null,
    val id: UUID? = null,
    val equipments: Set<EquipmentDto>? = null

)
