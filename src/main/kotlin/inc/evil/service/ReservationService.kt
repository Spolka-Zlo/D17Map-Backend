package inc.evil.service

import inc.evil.dto.ReservationDayDto
import inc.evil.dto.ReservationPostDto
import inc.evil.dto.UserReservationDto
import inc.evil.tables.Reservation
import org.koin.core.component.KoinComponent
import inc.evil.tables.Classrooms
import inc.evil.tables.Users
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime

class ReservationService() : KoinComponent {

    fun getGivenDayReservations(day: String): List<ReservationDayDto> {
//        val date = LocalDate.parse(day)
//        val reservations = reservationDAO.getAllReservationsForDay(date)
//        return reservations.map { ReservationMapper.entityToDayDto(it) }
        return listOf()
    }

    fun createReservation(reservationRequest: ReservationPostDto) {

        Reservation.new {
            name = reservationRequest.name
            date = reservationRequest.date.toJavaLocalDate()
            startTime = reservationRequest.startTime.toJavaLocalTime()
            endTime = reservationRequest.endTime.toJavaLocalTime()
            type = reservationRequest.type
        }
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
