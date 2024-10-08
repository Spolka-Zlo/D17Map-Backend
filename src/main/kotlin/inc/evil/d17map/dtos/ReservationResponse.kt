package inc.evil.d17map.dtos

import inc.evil.d17map.enums.ReservationType
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationResponse(
    val id: UUID,
    val title: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroom: ClassroomResponse,
    val type: ReservationType,
    val user: UserResponse
)
