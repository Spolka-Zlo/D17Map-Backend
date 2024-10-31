package inc.evil.d17map.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import inc.evil.d17map.enums.ReservationType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationRequest(
    @field:NotBlank(message = "Title must not be blank.")
    val title: String,

    @field:NotBlank(message = "Description must not be blank.")
    val description: String,

    @Schema(type = "string", format = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @field:NotNull(message = "Date must not be null.")
    @field:FutureOrPresent(message = "Date must be today or in the future.")
    val date: LocalDate,

    @Schema(type = "string", format = "time", example = "11:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @field:NotNull(message = "Start time must not be null.")
    @field:FutureOrPresent(message = "Start time must be now or in the future.")
    val startTime: LocalTime,

    @Schema(type = "string", format = "time", example = "13:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @field:NotNull(message = "End time must not be null.")
    @field:Future(message = "End time must be in the future.")
    val endTime: LocalTime,

    @field:NotNull(message = "Classroom ID must not be null.")
    val classroomId: UUID,

    @field:NotNull(message = "Reservation type must not be null.")
    val type: ReservationType,

    @field:Min(value = 1, message = "Number of participants must be at least 1.")
    val numberOfParticipants: Int
)

data class ReservationUpdateRequest(
    @field:NotBlank(message = "Title must not be blank.")
    val title: String,

    @field:NotBlank(message = "Description must not be blank.")
    val description: String,

    @field:NotNull(message = "Reservation type must not be null.")
    val type: ReservationType
)
