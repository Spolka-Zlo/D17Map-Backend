package inc.evil.persistance.repositories

import inc.evil.persistance.entities.Reservation
import java.util.UUID

interface ReservationRepository : Repository<UUID, Reservation> {
    // TODO add type specific methods

}