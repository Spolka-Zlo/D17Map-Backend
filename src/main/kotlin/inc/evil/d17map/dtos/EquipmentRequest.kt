package inc.evil.d17map.dtos

import jakarta.validation.constraints.Size

data class EquipmentRequest(
    @field:Size(min = 1, max = 100, message = "Equipment name must be between 1 and 100 characters long.")
    val name: String
)