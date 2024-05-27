package inc.evil.mapper

import inc.evil.dto.*
import inc.evil.entities.ClassroomEntity
import inc.evil.entities.ReservationEntity
import inc.evil.entities.UserEntity
import inc.evil.enums.ReservationType
import java.util.*

object ReservationMapper {

    fun toFullDto(
        reservationEntity: ReservationEntity,
        classroomEntity: ClassroomEntity,
        userEntity: UserEntity
    ): ReservationFullDto {
        val classroomDto = ClassroomSummaryDto(
            id = UUID.fromString(classroomEntity.id),
            name = classroomEntity.description
        )

        val userDto = UserSummaryDto(
            id = userEntity.id,
            email = userEntity.email
        )

        return ReservationFullDto(
            id = reservationEntity.id,
            name = reservationEntity.name,
            classroom = classroomDto,
            startDate = reservationEntity.startDate,
            endDate = reservationEntity.endDate,
            user = userDto
        )
    }

    fun toEntity(dto: ReservationFullDto, reservationType : ReservationType): ReservationEntity {
        return ReservationEntity(
            id = dto.id,
            userId = dto.user.id,
            classroomId = dto.classroom.id.toString(),
            name = dto.name,
            startDate = dto.startDate,
            endDate = dto.endDate,
            type = reservationType
        )
    }
}
