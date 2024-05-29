package inc.evil.mapper

import inc.evil.dto.ClassroomBasicInfoDto
import inc.evil.dto.ReservationDayDto
import inc.evil.dto.ReservationPostDto
import inc.evil.dto.UserReservationDto
import inc.evil.entities.ReservationEntity
import kotlinx.datetime.atTime
import java.util.*

object ReservationMapper {
    fun entityToDayDto(entity: ReservationEntity): ReservationDayDto {
        return ReservationDayDto(
            id = entity.id,
            type = entity.type,
            startTime = entity.startTime.time,
            endTime = entity.endTime.time,
            classroom = ClassroomBasicInfoDto(
                id = entity.classroomId,
                name = "Unknown"
            )
        )
    }

    fun postDtoToEntity(dto: ReservationPostDto): ReservationEntity {
        val startDateTime = dto.date.atTime(dto.startTime)
        val endDateTime = dto.date.atTime(dto.endTime)

        return ReservationEntity(
            id = dto.id ?: UUID.randomUUID(),
            userId = dto.userId,
            classroomId = dto.classroomId,
            name = dto.name,
            date = dto.date,
            startTime = startDateTime,
            endTime = endDateTime,
            type = dto.type
        )
    }

    fun entityToUserReservationDto(entity: ReservationEntity): UserReservationDto {
        return UserReservationDto(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            date = entity.date,
            startTime = entity.startTime.time,
            endTime = entity.endTime.time,
            classroomId = entity.classroomId
        )
    }
}
