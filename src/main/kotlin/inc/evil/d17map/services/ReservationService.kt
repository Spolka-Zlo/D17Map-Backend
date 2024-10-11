package inc.evil.d17map.services

import inc.evil.d17map.dtos.ReservationDto
import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.mappers.toReservationDto
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
    fun getGivenDayReservations(date: LocalDate): List<ReservationDto> {
        val reservations = reservationRepository.findAllByDate(date)
        return reservations.map { toReservationDto(it) }
    }

    fun getReservationById(id: UUID): ReservationDto {
        val reservation = reservationRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Reservation with id '$id' not found") }
        return toReservationDto(reservation)
    }

    fun createReservation(reservationDto: ReservationDto): ReservationDto {
        val classroom = classroomRepository.findById(reservationDto.classroomId!!)
            .orElseThrow { EntityNotFoundException("Classroom with id '${reservationDto.classroomId}' not found") }

        val user = userRepository.findById(reservationDto.userId!!)
            .orElseThrow { EntityNotFoundException("User with id '${reservationDto.userId}' not found") }

        val reservation = Reservation(
            title = reservationDto.title,
            date = reservationDto.date,
            startTime = reservationDto.startTime,
            endTime = reservationDto.endTime,
            classroom = classroom,
            type = reservationDto.type,
            user = user
        )

        val savedReservation = reservationRepository.save(reservation)
        return toReservationDto(savedReservation)
    }

    fun getUserFutureReservations(userId: UUID): List<ReservationDto> {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User with id '$userId' not found") }

        val futureReservations = user.reservations.filter { it.date.isAfter(LocalDate.now()) }
        return futureReservations.map { toReservationDto(it) }
    }
}
