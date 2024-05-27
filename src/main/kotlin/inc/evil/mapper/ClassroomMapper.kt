package inc.evil.mapper

import inc.evil.dto.ClassroomDetailsDto
import inc.evil.dto.ClassroomFullDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.entities.ClassroomDetailsEntity
import inc.evil.entities.ClassroomEntity
object ClassroomMapper {

    fun toFullDto(classroomEntity: ClassroomEntity, detailsEntity: ClassroomDetailsEntity): ClassroomFullDto {
        return ClassroomFullDto(
            id = classroomEntity.id,
            description = classroomEntity.description,
            details = ClassroomDetailsDto(
                numberOfSeats = detailsEntity.numberOfSeats,
                equipment = detailsEntity.equipment.split(", ")
            )
        )
    }

    fun toClassroomSummaryDto(classroomEntity: ClassroomEntity): ClassroomSummaryDto {
        return ClassroomSummaryDto(
            id = classroomEntity.id,
            name = classroomEntity.description
        )
    }

    fun toClassroomDetailsDto(detailsEntity: ClassroomDetailsEntity): ClassroomDetailsDto {
        return ClassroomDetailsDto(
            numberOfSeats = detailsEntity.numberOfSeats,
            equipment = detailsEntity.equipment.split(", ")
        )
    }

    // from ClassroomFullDto to Pair: ClassroomEntity and ClassroomDetailsEntity
    fun fromFullDto(fullDto: ClassroomFullDto): Pair<ClassroomEntity, ClassroomDetailsEntity> {
        val classroomEntity = ClassroomEntity(
            id = fullDto.id,
            description = fullDto.description,
            details = fullDto.details.equipment.joinToString(", ")
        )
        val detailsEntity = ClassroomDetailsEntity(
            classroomId = fullDto.id,
            numberOfSeats = fullDto.details.numberOfSeats,
            equipment = fullDto.details.equipment.joinToString(", ")
        )
        return Pair(classroomEntity, detailsEntity)
    }
}
