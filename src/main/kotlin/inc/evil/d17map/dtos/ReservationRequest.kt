package inc.evil.d17map.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import inc.evil.d17map.enums.ReservationType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationRequest(
    @field:Size(min = 1, max = 100, message = "Reservation title must be between 1 and 100 characters long.")
    val title: String,

    @field:Size(min = 1, max = 100, message = "Reservation description must be between 1 and 100 characters long.")
    val description: String,

    @Schema(type = "string", format = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @field:FutureOrPresent(message = "Date must be today or in the future.")
    val date: LocalDate,

    @Schema(type = "string", format = "time", example = "11:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val startTime: LocalTime,

    @Schema(type = "string", format = "time", example = "13:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val classroomId: UUID,
    val type: ReservationType,

    @field:Positive(message = "Number of participants must be at least 1.")
    val numberOfParticipants: Int
)

data class ReservationUpdateRequest(
    @field:Size(min = 1, max = 100, message = "Reservation title must be between 1 and 100 characters long.")
    val title: String,

    @field:Size(min = 1, max = 100, message = "Reservation description must be between 1 and 100 characters long.")
    val description: String,

    val type: ReservationType
)
