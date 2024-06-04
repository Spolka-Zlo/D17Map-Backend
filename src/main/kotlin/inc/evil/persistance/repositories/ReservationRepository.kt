package inc.evil.persistance.repositories

import inc.evil.persistance.entities.Reservation
import java.util.UUID

interface ReservationRepository : Repository<UUID, Reservation> {
    fun findByUserId(userId: UUID): List<Reservation>
    fun findFutureReservationsByUserId(userId: UUID): List<Reservation>
}
