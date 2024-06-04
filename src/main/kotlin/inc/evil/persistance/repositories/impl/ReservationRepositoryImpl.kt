package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Reservation
import inc.evil.persistance.entities.Reservations
import inc.evil.persistance.repositories.ReservationRepository
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID
import java.time.LocalDate.now

class ReservationRepositoryImpl : BaseRepository<UUID, Reservation>(Reservation), ReservationRepository {
    override fun findByUserId(userId: UUID): List<Reservation> = transaction {
        Reservation.find { Reservations.user eq userId }.toList()
    }

    override fun findFutureReservationsByUserId(userId: UUID): List<Reservation> = transaction {
        val currentDate = now()
        Reservation.find { (Reservations.user eq userId) and (Reservations.date greaterEq currentDate) }.toList()
    }
}
