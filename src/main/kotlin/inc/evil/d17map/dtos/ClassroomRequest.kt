package inc.evil.d17map.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.util.*

data class ClassroomRequest(
    @field:NotBlank(message = "Name must not be blank.")
    @field:Size(max = 100, message = "Name must not exceed 100 characters.")
    val name: String,

    @field:NotBlank(message = "Description must not be blank.")
    @field:Size(max = 255, message = "Description must not exceed 255 characters.")
    val description: String,

    @field:Positive(message = "Capacity must be a positive number.")
    val capacity: Int,

    val equipmentIds: Set<UUID>
)
