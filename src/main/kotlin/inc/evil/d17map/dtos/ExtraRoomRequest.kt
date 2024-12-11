package inc.evil.d17map.dtos

import jakarta.validation.constraints.Size

class ExtraRoomRequest (
    @field:Size(min = 1, max = 100, message = "Extra room name must be between 1 and 100 characters long.")
    var name: String,

    @field:Size(min = 1, max = 255, message = "Description must be between 1 and 255 characters long.")
    var description: String,
    var type: String,
    val modelKey: String
)