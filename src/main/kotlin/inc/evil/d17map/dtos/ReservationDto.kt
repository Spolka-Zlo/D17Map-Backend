package inc.evil.d17map.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import inc.evil.d17map.enums.ReservationType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class ReservationDto(
    val id: UUID? = null,
    val title: String,

    @Schema(type = "string",  example = "24-12-2024")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    val date: LocalDate,

    @Schema(type = "string", format="time", example = "11:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val startTime: LocalTime,

    @Schema(type = "string", format="time", example = "13:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    val endTime: LocalTime,
    val classroomId: UUID? = null,
    val classroom: ClassroomDto? = null,
    val type: ReservationType,
    val userId: UUID? = null,
    val user: UserDto? = null
)
