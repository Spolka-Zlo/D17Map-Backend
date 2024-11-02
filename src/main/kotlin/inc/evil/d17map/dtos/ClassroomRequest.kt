package inc.evil.d17map.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.util.*

data class ClassroomRequest(
    @field:Size(min = 1, max = 100, message = "Classroom name must be between 1 and 100 characters long.")
    val name: String,

    @field:NotBlank(message = "Description must not be blank.")
    @field:Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters long.")
    @field:Size(max = 255, message = "Description must not exceed 255 characters.")
    val description: String,

    @field:Positive(message = "Capacity must be a positive number.")
    val capacity: Int,

    val equipmentIds: Set<UUID>
)
