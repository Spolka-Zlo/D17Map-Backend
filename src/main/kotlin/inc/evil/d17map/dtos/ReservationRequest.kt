package inc.evil.d17map.dtos

import inc.evil.d17map.enums.ReservationType
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationRequest(
    val title: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classroomId: UUID,
    val type: ReservationType,
    val userId: UUID
)
