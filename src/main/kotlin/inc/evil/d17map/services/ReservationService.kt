package inc.evil.d17map.services

import inc.evil.d17map.dtos.ReservationRequest
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.mappers.ReservationMapper
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository,
    private val classroomRepository: ClassroomRepository,
    private val userRepository: UserRepository
) {
    fun getGivenDayReservations(date: LocalDate): List<ReservationResponse> {
        val reservations = reservationRepository.findAllByDate(date)
        return reservations.map { ReservationMapper.mapToReservationResponse(it) }
    }

    fun createReservation(reservationRequest: ReservationRequest): ReservationResponse {
        val classroom = classroomRepository.findById(reservationRequest.classroomId)
            .orElseThrow { EntityNotFoundException("Classroom with id '${reservationRequest.classroomId}' not found") }

        val user = userRepository.findById(reservationRequest.userId)
            .orElseThrow { EntityNotFoundException("User with id '${reservationRequest.userId}' not found") }

        val reservation = Reservation(
            title = reservationRequest.title,
            date = reservationRequest.date,
            startTime = reservationRequest.startTime,
            endTime = reservationRequest.endTime,
            classroom = classroom,
            type = reservationRequest.type,
            user = user
        )

        val savedReservation = reservationRepository.save(reservation)
        return ReservationMapper.mapToReservationResponse(savedReservation)
    }

    fun getUserReservations(userId: UUID): List<ReservationResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User with id '$userId' not found") }

        return user.reservations.map { ReservationMapper.mapToReservationResponse(it) }
    }
}
