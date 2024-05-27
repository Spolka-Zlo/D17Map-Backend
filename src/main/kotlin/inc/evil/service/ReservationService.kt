package inc.evil.service

import inc.evil.dao.ReservationDAO
import inc.evil.dto.ReservationFullDto
import org.koin.core.component.KoinComponent
import java.util.*

class ReservationService(private val reservationDAO: ReservationDAO) : KoinComponent {
    fun getReservations(): List<ReservationFullDto> {
        return listOf()
    }

    fun getReservationById(id: UUID): ReservationFullDto? {
        return null
    }

    fun post(reservationFullDto: ReservationFullDto): ReservationFullDto? {
        return null
    }

    // TODO change dto type
    fun patch(reservationFullDto: ReservationFullDto): ReservationFullDto? {
        return null
    }

    fun delete(id :UUID) {
        reservationDAO.deleteReservation(id)
    }
}
