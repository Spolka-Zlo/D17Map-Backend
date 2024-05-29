package inc.evil.mapper

import inc.evil.dto.ClassroomSummaryDto
import inc.evil.dto.ClassroomBasicInfoDto
import inc.evil.dto.ClassroomPostDto
import inc.evil.entities.ClassroomEntity
import inc.evil.entities.ClassroomDetailsEntity
import java.util.*

object ClassroomMapper {

    fun toSummaryDto(classroomEntity: ClassroomEntity): ClassroomSummaryDto {
        return ClassroomSummaryDto(
            id = classroomEntity.id,
            name = classroomEntity.description,
            capacity = classroomEntity.details.numberOfSeats,
            equipment = classroomEntity.details.equipment.split(", ")
        )
    }

    fun toEntity(classroomPostDto: ClassroomPostDto): ClassroomEntity {
        val classroomId = classroomPostDto.id ?: UUID.randomUUID()
        val details = ClassroomDetailsEntity(
            classroomId = classroomId,
            numberOfSeats = classroomPostDto.capacity,
            equipment = classroomPostDto.equipment.joinToString(", ")
        )
        return ClassroomEntity(
            id = classroomId,
            description = classroomPostDto.description,
            details = details
        )
    }

    fun toBasicInfoDto(classroomEntity: ClassroomEntity): ClassroomBasicInfoDto {
        return ClassroomBasicInfoDto(
            id = classroomEntity.id,
            name = " "
        )
    }
}
