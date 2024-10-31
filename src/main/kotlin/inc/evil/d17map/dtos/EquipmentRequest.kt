package inc.evil.d17map.dtos

import jakarta.validation.constraints.NotBlank

data class EquipmentRequest(
    @field:NotBlank(message = "Equipment name must not be blank.")
    val name: String
)