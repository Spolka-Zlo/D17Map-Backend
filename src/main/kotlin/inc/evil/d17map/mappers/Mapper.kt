package inc.evil.d17map.mappers

import inc.evil.d17map.dtos.ClassroomDto
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.dtos.UserDto
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Reservation

fun toClassroomDto(classroom: Classroom?): ClassroomDto {
    val equipmentResponses = classroom?.equipments?.map { equipment ->
        EquipmentResponse(id = equipment.id!!, name = equipment.name)
    }?.toSet()

    return ClassroomDto(
        id = classroom?.id!!,
        name = classroom.name,
        description = classroom.description,
        capacity = classroom.capacity,
        equipments = equipmentResponses
    )
}

fun toReservationDto(reservation: Reservation): ReservationResponse {
    val classroomResponse = ClassroomDto(
        id = reservation.classroom.id!!,
        name = reservation.classroom.name,
        description = reservation.classroom.description,
        capacity = reservation.classroom.capacity,
        equipments = reservation.classroom.equipments.map { equipment ->
            EquipmentResponse(id = equipment.id!!, name = equipment.name)
        }.toSet()
    )

    val userResponse = UserDto(
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
