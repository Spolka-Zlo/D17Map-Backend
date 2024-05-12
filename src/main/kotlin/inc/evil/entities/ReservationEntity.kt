package inc.evil.entities

import inc.evil.tables.Reservation
import inc.evil.enums.ReservationType
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime
import java.util.*

data class ReservationEntity(
    val id: UUID,
    val userId: UUID,
    val classroomId: String,
    val name: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val type: ReservationType
) {
    companion object {
        fun fromResultRow(row: ResultRow): ReservationEntity {
            return ReservationEntity(
                id = row[Reservation.id],
                userId = row[Reservation.userId],
                classroomId = row[Reservation.classroomId],
                name = row[Reservation.name],
                startDate = row[Reservation.startDate],
                endDate = row[Reservation.endDate],
                type = ReservationType.valueOf(row[Reservation.type].toString())
            )
        }
    }
}
