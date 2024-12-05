package inc.evil.d17map.dtos

import inc.evil.d17map.entities.Floor
import java.util.*

class ExtraRoomResponse (
    val id: UUID,
    val name: String,
    val modelKey: String,
    val description: String,
    val type: String,
    val floor: Floor
)
