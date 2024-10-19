package inc.evil.d17map.services

import inc.evil.d17map.dtos.ReservationDto
import inc.evil.d17map.entities.Reservation
import inc.evil.d17map.entities.User
import inc.evil.d17map.findOne
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

    fun getReservationById(id: UUID): ReservationDto? {
        val reservation = reservationRepository.findOne(id)
        return reservation?.let { toReservationDto(it) }
    }

    fun createReservation(reservationDto: ReservationDto): ReservationDto? {
        val classroom = classroomRepository.findOne(reservationDto.classroomId!!)
        val user = userRepository.findOne(reservationDto.userId!!)
        val reservation = classroom?.let {
            user?.let { it1 ->
                Reservation(
                    title = reservationDto.title,
                    date = reservationDto.date,
                    startTime = reservationDto.startTime,
                    endTime = reservationDto.endTime,
                    classroom = it,
                    type = reservationDto.type,
                    user = it1
                )
            }
        }
        val savedReservation = reservation?.let { reservationRepository.save(it) }
        return savedReservation?.let { toReservationDto(it) }
    }

    fun getUserFutureReservations(userId: UUID): List<ReservationDto>? {
        val user: User? = userRepository.findOne(userId)
        val futureReservations = user?.reservations?.filter { it.date.isAfter(LocalDate.now()) }
        return futureReservations?.map { toReservationDto(it) }
    }
}
