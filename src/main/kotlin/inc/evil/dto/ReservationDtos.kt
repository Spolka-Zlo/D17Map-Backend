package inc.evil.dto

import inc.evil.plugins.UUIDSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class ReservationFullDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val classroom: ClassroomSummaryDto,
    @Contextual val startDate: LocalDateTime,
    @Contextual val endDate: LocalDateTime,
    val user: UserSummaryDto
)
