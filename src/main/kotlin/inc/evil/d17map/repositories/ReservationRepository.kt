package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface ReservationRepository : JpaRepository<Reservation, UUID> {
    fun findAllByDate(date: LocalDate): List<Reservation>
    fun findAllByDateBetween(startDate: LocalDate, endDate: LocalDate): List<Reservation>
    fun findAllByUserAndDateBetween(user: User, startDate: LocalDate, endDate: LocalDate): List<Reservation>
}