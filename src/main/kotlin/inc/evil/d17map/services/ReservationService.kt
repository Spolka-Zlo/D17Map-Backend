package inc.evil.d17map.services

import inc.evil.d17map.exceptions.ClassroomNotFoundException
import inc.evil.d17map.exceptions.ReservationNotFoundException
import inc.evil.d17map.exceptions.UserNotFoundException
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
import java.time.LocalDateTime
import java.time.LocalTime
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
        val classroom = classroomRepository.findOne(updateRequest.classroomId) ?: throw ClassroomNotFoundException(updateRequest.classroomId)

        reservation.run {
            this.title = updateRequest.title
            this.description = updateRequest.description
            this.type = updateRequest.type
            this.classroom = classroom
        }

        val updatedReservation = reservationRepository.save(reservation)
        return toReservationResponse(updatedReservation)
    }

    fun getCurrentOrFutureEvents(currentDateTime: LocalDateTime): List<ReservationResponse> {
        val currentDate = currentDateTime.toLocalDate()
        val currentTime = currentDateTime.toLocalTime()

        val reservations = reservationRepository.findAllCurrentOrFutureEvents(currentDate, currentTime)
        return reservations.map { toReservationResponse(it) }
    }
    fun updateReservationAdmin(id: UUID, adminUpdateRequest: ReservationRequest): ReservationResponse {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { ReservationNotFoundException(id) }

        reservation.title = adminUpdateRequest.title
        reservation.description = adminUpdateRequest.description
        reservation.date = adminUpdateRequest.date
        reservation.startTime = adminUpdateRequest.startTime
        reservation.endTime = adminUpdateRequest.endTime
        reservation.classroom = classroomRepository.findById(adminUpdateRequest.classroomId)
            .orElseThrow { ReservationNotFoundException(id) }
        reservation.type = adminUpdateRequest.type
        reservation.numberOfParticipants = adminUpdateRequest.numberOfParticipants

        val updatedReservation = reservationRepository.save(reservation)
        return toReservationResponse(updatedReservation)
    }

    fun getReservationsForUser(userId: UUID): List<ReservationResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException(userId.toString()) }

        val reservations = reservationRepository.findAllByUserId(user.id!!)
        return reservations.map { toReservationResponse(it) }
    }

    fun getAllReservations(): List<ReservationResponse> {
        val reservations = reservationRepository.findAll()
        return reservations.map { toReservationResponse(it) }
    }
}
