package inc.evil.d17map.dtos

import inc.evil.d17map.enums.ReservationType
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationDto(
    val id: UUID? = null,
    val title: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroomId: UUID? = null,
    val classroom: ClassroomDto? = null,
    val type: ReservationType,
    val userId: UUID? = null,
    val user: UserDto? = null,
    val numberOfParticipants: Int = 0
)
