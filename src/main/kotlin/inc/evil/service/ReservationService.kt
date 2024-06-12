package inc.evil.service

import inc.evil.dto.ClassroomBasicInfoDto
import inc.evil.dto.ReservationDayDto
import inc.evil.dto.ReservationPostDto
import inc.evil.dto.UserReservationDto
import inc.evil.persistance.entities.Classroom
import inc.evil.persistance.entities.Reservation
import inc.evil.persistance.entities.Reservations
import inc.evil.persistance.entities.User
import inc.evil.persistance.repositories.ReservationRepository
import kotlinx.datetime.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class ReservationService(private val reservationRepository: ReservationRepository) : KoinComponent {

    fun getGivenDayReservations(day: String) = transaction {

        val dayAsDate = LocalDate.parse(day)

        val dbReservations = Reservation.find { Reservations.date eq dayAsDate.toJavaLocalDate() }.toList()

        dbReservations.map {
            ReservationDayDto(
                it.id.value,
                it.type,
                it.startTime.toKotlinLocalTime(),
                it.endTime.toKotlinLocalTime(),
                ClassroomBasicInfoDto(it.classroom.id.value, it.classroom.name)
            )
        }
    }

    fun createReservation(reservationRequest: ReservationPostDto) = transaction {
        val dbUser = User.findById(reservationRequest.userId)!! // TODO remove assertion !!
        // TODO exception handling ...

        val dbClassroom = Classroom.findById(reservationRequest.classroomId)!!

        val dbReservation = Reservation.new {
            title=reservationRequest.title
            date=reservationRequest.date.toJavaLocalDate()
            startTime = reservationRequest.startTime.toJavaLocalTime()
            endTime = reservationRequest.endTime.toJavaLocalTime()
            type=reservationRequest.type
            user = dbUser
            classroom = dbClassroom
        }

        ReservationPostDto(
            dbReservation.id.value,
            dbReservation.title,
            dbReservation.type,
            dbReservation.user.id.value,
            dbReservation.date.toKotlinLocalDate(),
            dbReservation.startTime.toKotlinLocalTime(),
            dbReservation.endTime.toKotlinLocalTime(),
            dbReservation.classroom.id.value,
            )

    }


    fun getUserReservations(userId: String): List<UserReservationDto> {
        TODO("not implemented yet")
    }


    fun getUserFutureReservations(userId: String): List<UserReservationDto> {
        TODO("not implemented yet")
    }

}
