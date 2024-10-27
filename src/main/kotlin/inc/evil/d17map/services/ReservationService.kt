package inc.evil.d17map.services

import inc.evil.d17map.ClassroomNotFoundException
import inc.evil.d17map.ReservationNotFoundException
import inc.evil.d17map.UserNotFoundException
import inc.evil.d17map.dtos.ReservationRequest
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.dtos.ReservationUpdateRequest
import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.enums.ReservationType
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toReservationResponse
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import java.util.*

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

    fun getReservationById(id: UUID): ReservationResponse {
        val reservation = reservationRepository.findOne(id) ?: throw ReservationNotFoundException(id)
        return toReservationResponse(reservation)
    }

    fun createReservation(reservationRequest: ReservationRequest): ReservationResponse {
        val classroom = classroomRepository.findOne(reservationRequest.classroomId) ?: throw ClassroomNotFoundException(
            reservationRequest.classroomId
        )
        val username = SecurityContextHolder.getContext().authentication.name

        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val reservation = Reservation(
            id = classroom.id!!,
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

    fun getUserFutureReservations(): List<ReservationResponse>? {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)


        val futureReservations = reservationRepository.findAllFuture(
            user.id!!,
            LocalDate.now(),
            LocalTime.now()
        )
        return futureReservations.map { toReservationResponse(it) }
    }


    fun getUserWeekReservations(monday: LocalDate): List<ReservationResponse> {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(username) ?: throw UserNotFoundException(username)

        val endOfWeek = monday.plusDays(6)

        val reservations = reservationRepository.findAllByUserAndDateBetween(user.id!!, monday, endOfWeek)
        return reservations.map { toReservationResponse(it) }
    }

    fun getReservationTypes(): List<ReservationType> {
        return ReservationType.entries
    }

    fun removeReservation(id: UUID) =
        if (reservationRepository.existsById(id)) reservationRepository.deleteById(id)
        else throw ReservationNotFoundException(id)


    fun updateReservation(id: UUID, updateRequest: ReservationUpdateRequest): ReservationResponse {
        val reservation = reservationRepository.findOne(id) ?: throw ReservationNotFoundException(id)

        reservation.run {
            this.title = updateRequest.title
            this.description = updateRequest.description
            this.type = updateRequest.type
        }

        val updatedReservation = reservationRepository.save(reservation)
        return toReservationResponse(updatedReservation)
    }

}
