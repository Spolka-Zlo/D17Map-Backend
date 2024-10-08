package inc.evil.d17map.mappers

import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.dtos.UserResponse
import inc.evil.d17map.entities.Reservation

object ReservationMapper {

    fun mapToReservationResponse(reservation: Reservation): ReservationResponse {
        val classroomResponse = ClassroomResponse(
            id = reservation.classroom.id!!,
            name = reservation.classroom.name,
            description = reservation.classroom.description,
            capacity = reservation.classroom.capacity,
            equipments = reservation.classroom.equipments.map { equipment ->
                EquipmentResponse(id = equipment.id!!, name = equipment.name)
            }.toSet()
        )

        val userResponse = UserResponse(
            id = reservation.user.id!!,
            email = reservation.user.email,
            userType = reservation.user.userType
        )

        return ReservationResponse(
            id = reservation.id!!,
            title = reservation.title,
            date = reservation.date,
            startTime = reservation.startTime,
            endTime = reservation.endTime,
            classroom = classroomResponse,
            type = reservation.type,
            user = userResponse
        )
    }
}
