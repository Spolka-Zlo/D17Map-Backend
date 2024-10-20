package inc.evil.d17map.mappers

import inc.evil.d17map.dtos.ClassroomDto
import inc.evil.d17map.dtos.EquipmentDto
import inc.evil.d17map.dtos.ReservationDto
import inc.evil.d17map.dtos.UserDto
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Reservation

fun toClassroomDto(classroom: Classroom?): ClassroomDto {
    val equipmentResponses = classroom?.equipments?.map { equipment ->
        EquipmentDto(id = equipment.id!!, name = equipment.name)
    }?.toSet()

    return ClassroomDto(
        id = classroom?.id!!,
        name = classroom.name,
        description = classroom.description,
        capacity = classroom.capacity,
        equipments = equipmentResponses
    )
}

fun toReservationDto(reservation: Reservation): ReservationDto {
    val classroomResponse = ClassroomDto(
        id = reservation.classroom.id!!,
        name = reservation.classroom.name,
        description = reservation.classroom.description,
        capacity = reservation.classroom.capacity,
        equipments = reservation.classroom.equipments.map { equipment ->
            EquipmentDto(id = equipment.id!!, name = equipment.name)
        }.toSet()
    )

    val userResponse = UserDto(
        id = reservation.user.id!!,
        email = reservation.user.email,
        userType = reservation.user.userType
    )

    return ReservationDto(
        id = reservation.id!!,
        title = reservation.title,
        date = reservation.date,
        startTime = reservation.startTime,
        endTime = reservation.endTime,
        classroom = classroomResponse,
        type = reservation.type,
        user = userResponse,
        numberOfParticipants = reservation.numberOfParticipants
    )
}
