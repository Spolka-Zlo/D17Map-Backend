package inc.evil.entities

import inc.evil.enums.ReservationType
import inc.evil.tables.Reservation
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime
import java.util.*

data class ReservationEntity(
    val id: UUID,
    val userId: UUID,
    val classroomId: UUID,
    val name: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val type: ReservationType
) {
    companion object {
        fun fromResultRow(row: ResultRow): ReservationEntity {
            return ReservationEntity(
                id = row[Reservation.id].value,
                userId = row[Reservation.userId].value,
                classroomId = row[Reservation.classroomId].value,
                name = row[Reservation.name],
                startDate = row[Reservation.startDate],
                endDate = row[Reservation.endDate],
                type = row[Reservation.type]
            )
        }
    }
}
