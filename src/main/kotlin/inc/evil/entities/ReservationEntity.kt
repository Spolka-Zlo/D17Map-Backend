package inc.evil.entities

import inc.evil.enums.ReservationType
import inc.evil.tables.Reservation
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.ResultRow

import java.util.*

data class ReservationEntity(
    val id: UUID,
    val userId: UUID,
    val classroomId: UUID,
    val name: String,
    val date: LocalDate,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val type: ReservationType
) {
    companion object {
        fun fromResultRow(row: ResultRow): ReservationEntity {
            return ReservationEntity(
                id = row[Reservation.id].value,
                userId = row[Reservation.userId].value,
                classroomId = row[Reservation.classroomId].value,
                name = row[Reservation.name],
                date = row[Reservation.date].toKotlinLocalDate(),
                startTime = row[Reservation.startTime].toKotlinLocalDateTime(),
                endTime = row[Reservation.endTime].toKotlinLocalDateTime(),
                type = row[Reservation.type]
            )
        }
    }
}
