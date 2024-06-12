package inc.evil.dto

import inc.evil.config.UUIDSerializer
import inc.evil.enums.ReservationType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.serializers.LocalTimeComponentSerializer
import kotlinx.datetime.serializers.LocalTimeIso8601Serializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ReservationDayDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val type: ReservationType,
    @Serializable(with = LocalTimeIso8601Serializer::class) val startTime: LocalTime,
    @Serializable(with = LocalTimeIso8601Serializer::class) val endTime: LocalTime,
    val classroom: ClassroomBasicInfoDto
)

@Serializable
data class ReservationPostDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val title: String,
    val type: ReservationType,
    @Serializable(with = UUIDSerializer::class) val userId: UUID,
    @Serializable(with = LocalDateIso8601Serializer::class) val date: LocalDate,
    @Serializable(with = LocalTimeIso8601Serializer::class) val startTime: LocalTime,
    @Serializable(with = LocalTimeIso8601Serializer::class) val endTime: LocalTime,
    @Serializable(with = UUIDSerializer::class) val classroomId: UUID
)

@Serializable
data class UserReservationDto(
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
    val title: String,
    val type: ReservationType,
    @Serializable(with = LocalDateIso8601Serializer::class) val date: LocalDate,
    @Serializable(with = LocalTimeIso8601Serializer::class) val startTime: LocalTime,
    @Serializable(with = LocalTimeIso8601Serializer::class) val endTime: LocalTime,
    @Serializable(with = UUIDSerializer::class) val classroomId: UUID
)


