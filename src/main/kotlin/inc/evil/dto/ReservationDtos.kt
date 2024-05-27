package inc.evil.dto

import inc.evil.plugins.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

import java.util.*

@Serializable
data class ReservationFullDto(
    @Serializable (with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val classroom: ClassroomSummaryDto,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val user: UserSummaryDto
    )