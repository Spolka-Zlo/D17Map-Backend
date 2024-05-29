package inc.evil.dao

import inc.evil.entities.ReservationEntity
import inc.evil.tables.Classroom
import inc.evil.tables.Reservations
import inc.evil.tables.User
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDateTime


class ReservationDAO : KoinComponent {
    fun getReservationById(reservationId: UUID): ReservationEntity? {
        return transaction {
            Reservations.select { Reservations.id eq reservationId }
                .map { ReservationEntity.fromResultRow(it) }
                .singleOrNull()
        }
    }

    fun getUserReservations(userId: String): List<ReservationEntity> {
        return transaction {
            Reservations.select { Reservations.userId eq UUID.fromString(userId) }
                .map { ReservationEntity.fromResultRow(it) }
        }
    }

    fun getUserFutureReservations(userId: String): List<ReservationEntity> {
        val currentTime =  LocalDateTime.now()
        return transaction {
            Reservations.select {
                (Reservations.userId eq UUID.fromString(userId)) and
                        (Reservations.startTime greaterEq currentTime)
            }.map { ReservationEntity.fromResultRow(it) }
        }
    }

    fun getAllReservationsForDay(day: LocalDate): List<ReservationEntity> {
        return transaction {
            Reservations.selectAll()
                .filter { it[Reservations.date].toKotlinLocalDate() == day }
                .map { ReservationEntity.fromResultRow(it) }
        }
    }

    fun getAllReservations(): List<ReservationEntity> {
        return transaction {
            Reservations.selectAll()
                .map { ReservationEntity.fromResultRow(it) }
        }
    }

    fun createReservation(reservation: ReservationEntity) {
        transaction {
            Reservations.insert {
                it[id] = EntityID(reservation.id, Reservations)
                it[userId] = EntityID(reservation.userId, User)
                it[classroomId] = EntityID(reservation.classroomId, Classroom)
                it[name] = reservation.name
                it[startTime] = reservation.startTime.toJavaLocalDateTime()
                it[endTime] = reservation.endTime.toJavaLocalDateTime()
                it[type] = reservation.type
            }
        }
    }

    fun updateReservation(reservation: ReservationEntity) {
        transaction {
            Reservations.update({ Reservations.id eq reservation.id }) {
                it[userId] = EntityID(reservation.userId, User)
                it[classroomId] = EntityID(reservation.classroomId, Classroom)
                it[name] = reservation.name
                it[startTime] = reservation.startTime.toJavaLocalDateTime()
                it[endTime] = reservation.endTime.toJavaLocalDateTime()
                it[type] = reservation.type
            }
        }
    }

    fun deleteReservation(reservationId: UUID) {
        transaction {
            Reservations.deleteWhere { Reservations.id eq reservationId }
        }
    }
}
