package inc.evil.d17map.services

import inc.evil.d17map.ClassroomNotFoundException
import inc.evil.d17map.UserNotFoundException
import inc.evil.d17map.dtos.ReservationRequest
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toReservationResponse
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val classroomRepository: ClassroomRepository,
    private val userRepository: UserRepository
) {
    fun getGivenDayReservations(date: LocalDate): List<ReservationResponse> {
        val reservations = reservationRepository.findAllByDate(date)
        return reservations.map { toReservationResponse(it) }
    }
//    TODO uncomment and adjust when needed
//    fun getReservationById(id: UUID): ReservationResponse? {
//        if (!reservationRepository.existsById(id)) {
//            throw ReservationNotFoundException(id)
//        }
//        val reservation = reservationRepository.findOne(id)
//        return reservation?.let { toReservationResponse(it) }
//    }

    fun createReservation(reservationRequest: ReservationRequest): ReservationResponse {
        val classroom = classroomRepository.findOne(reservationRequest.classroomId) ?: throw ClassroomNotFoundException(
            reservationRequest.classroomId
        )
        val username = SecurityContextHolder.getContext().authentication.name

        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val reservation = Reservation(
            id = classroom.id,
            title = reservationRequest.title,
            description = reservationRequest.description,
            date = reservationRequest.date,
            startTime = reservationRequest.startTime,
            endTime = reservationRequest.endTime,
            classroom = classroom,
            type = reservationRequest.type,
            user = user,
            numberOfParticipants = reservationRequest.numberOfParticipants
        )

        val savedReservation = reservationRepository.save(reservation)
        return toReservationResponse(savedReservation)
    }

    fun getReservationsForWeek(monday: LocalDate): List<ReservationResponse> {
        val endOfWeek = monday.plusDays(6)
        val reservations = reservationRepository.findAllByDateBetween(monday, endOfWeek)
        return reservations.map { toReservationResponse(it) }
    }
//    TODO uncomment and adjust when necessary
//    fun getUserFutureReservations(userId: UUID): List<ReservationResponse>? {
//        if (!userRepository.existsById(userId)) {
//            throw UserNotFoundException(userId)
//        }
//        val user: User? = userRepository.findOne(userId)
//        val futureReservations = user?.reservations?.filter { it.date.isAfter(LocalDate.now()) }
//        return futureReservations?.map { toReservationResponse(it) }
//    }


    fun getUserWeekReservations(monday: LocalDate): List<ReservationResponse> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val endOfWeek = monday.plusDays(6)

        val reservations = reservationRepository.findAllByUserAndDateBetween(user, monday, endOfWeek)
        return reservations.map { toReservationResponse(it) }
    }
}
