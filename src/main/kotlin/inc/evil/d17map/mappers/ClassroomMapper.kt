package inc.evil.d17map.mappers

import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.entities.Classroom

object ClassroomMapper {
    fun mapToClassroomResponse(classroom: Classroom): ClassroomResponse {
        val equipmentResponses = classroom.equipments.map { equipment ->
            EquipmentResponse(id = equipment.id!!, name = equipment.name)
        }.toSet()

        return ClassroomResponse(
            id = classroom.id!!,
            name = classroom.name,
            description = classroom.description,
            capacity = classroom.capacity,
            equipments = equipmentResponses
        )
    }
}
