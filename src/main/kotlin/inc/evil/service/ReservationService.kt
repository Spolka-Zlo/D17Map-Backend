package inc.evil.service

import inc.evil.dto.ReservationDayDto
import inc.evil.dto.ReservationPostDto
import inc.evil.dto.UserReservationDto
import inc.evil.persistance.repositories.ReservationRepository
import org.koin.core.component.KoinComponent

class ReservationService(private val reservationRepository: ReservationRepository) : KoinComponent {

    fun getGivenDayReservations(day: String): List<ReservationDayDto> {
        TODO("not implemented yet")
    }

    fun createReservation(reservationRequest: ReservationPostDto) {
        TODO("Not yet implemented")
    }


    fun getUserReservations(userId: String): List<UserReservationDto> {
        TODO("not implemented yet")
    }


    fun getUserFutureReservations(userId: String): List<UserReservationDto> {
        TODO("not implemented yet")
    }

}
