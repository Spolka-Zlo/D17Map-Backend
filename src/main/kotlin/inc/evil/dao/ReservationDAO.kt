package inc.evil.dao

import inc.evil.entities.ReservationEntity
import inc.evil.tables.Classroom
import inc.evil.tables.Reservation
import inc.evil.tables.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.*

class ReservationDAO : KoinComponent {
    fun getReservationById(reservationId: UUID): ReservationEntity? {
        return transaction {
            Reservation.select { Reservation.id eq reservationId }
                .map { ReservationEntity.fromResultRow(it) }
                .singleOrNull()
        }
    }

    fun getAllReservations(): List<ReservationEntity> {
        return transaction {
            Reservation.selectAll()
                .map { ReservationEntity.fromResultRow(it) }
        }
    }

    fun createReservation(reservation: ReservationEntity) {
        transaction {
            Reservation.insert {
                it[id] = EntityID(reservation.id, Reservation)
                it[userId] = EntityID(reservation.userId, User)
                it[classroomId] = EntityID(reservation.classroomId, Classroom)
                it[name] = reservation.name
                it[startDate] = reservation.startDate
                it[endDate] = reservation.endDate
                it[type] = reservation.type
            }
        }
    }

    fun updateReservation(reservation: ReservationEntity) {
        transaction {
            Reservation.update({ Reservation.id eq reservation.id }) {
                it[userId] = EntityID(reservation.userId, User)
                it[classroomId] = EntityID(reservation.classroomId, Classroom)
                it[name] = reservation.name
                it[startDate] = reservation.startDate
                it[endDate] = reservation.endDate
                it[type] = reservation.type
            }
        }
    }

    fun deleteReservation(reservationId: UUID) {
        transaction {
            Reservation.deleteWhere { Reservation.id eq reservationId }
        }
    }
}

