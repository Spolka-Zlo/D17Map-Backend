package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Reservation
import inc.evil.persistance.repositories.ReservationRepository
import java.util.UUID

class ReservationRepositoryImpl: BaseRepository<UUID, Reservation>(Reservation), ReservationRepository {
}