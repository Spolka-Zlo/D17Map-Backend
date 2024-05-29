package inc.evil.service

import inc.evil.dao.ReservationDAO
import inc.evil.dto.ReservationDayDto
import inc.evil.dto.ReservationPostDto
import inc.evil.dto.UserReservationDto
import org.koin.core.component.KoinComponent
import kotlinx.datetime.LocalDate
import inc.evil.mapper.ReservationMapper


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
