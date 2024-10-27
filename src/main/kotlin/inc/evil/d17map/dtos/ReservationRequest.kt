package inc.evil.d17map.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import inc.evil.d17map.enums.ReservationType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationRequest(
    val title: String,
    val description: String,

    @Schema(type = "string", format = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,

    @Schema(type = "string", format = "time", example = "11:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val startTime: LocalTime,

    @Schema(type = "string", format = "time", example = "13:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val endTime: LocalTime,

    val classroomId: UUID,
    val type: ReservationType,
    val numberOfParticipants: Int
)

data class ReservationUpdateRequest(
    val title: String,
    val description: String,
    val type: ReservationType
)
