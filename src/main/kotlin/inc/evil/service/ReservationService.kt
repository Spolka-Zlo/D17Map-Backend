package inc.evil.service

import inc.evil.dto.ReservationDayDto
import inc.evil.dto.ReservationPostDto
import inc.evil.dto.UserReservationDto
import org.koin.core.component.KoinComponent
import kotlinx.datetime.LocalDate



class ReservationService() : KoinComponent {

    fun getGivenDayReservations(day: String): List<ReservationDayDto> {
//        val date = LocalDate.parse(day)
//        val reservations = reservationDAO.getAllReservationsForDay(date)
//        return reservations.map { ReservationMapper.entityToDayDto(it) }
        return listOf()
    }

    fun createReservation(reservationRequest: ReservationPostDto) {
//        val reservationEntity = ReservationMapper.postDtoToEntity(reservationRequest)
//        reservationDAO.createReservation(reservationEntity)
    }


    fun getUserReservations(userId: String): List<UserReservationDto> {
//        val userReservations = reservationDAO.getUserReservations(userId)
//        return userReservations.map { ReservationMapper.entityToUserReservationDto(it) }
        return listOf()
    }


    fun getUserFutureReservations(userId: String): List<UserReservationDto> {
//        val userFutureReservations = reservationDAO.getUserFutureReservations(userId)
//        return userFutureReservations.map { ReservationMapper.entityToUserReservationDto(it) }
        return listOf()
    }

}
