package inc.evil.d17map.mappers

import inc.evil.d17map.dtos.*
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Equipment
import inc.evil.d17map.entities.Reservation
import java.util.*


fun toEquipmentResponse(equipment: Equipment): EquipmentResponse =
    EquipmentResponse(
        id = equipment.id!!,
        name = equipment.name
    )


fun toClassroomResponse(classroom: Classroom): ClassroomResponse {
    return ClassroomResponse(
        name = classroom.name,
        description = classroom.description,
        capacity = classroom.capacity,
        id = classroom.id!!,
        equipmentIds = toEquipmentIds(classroom.equipments),
    )
}

fun toClassroomSummary(classroom: Classroom): ClassroomSummary =
    ClassroomSummary(
        id = classroom.id!!,
        name = classroom.name,
        capacity = classroom.capacity
    )


fun toEquipmentIds(equipments: Set<Equipment>): Set<UUID> = equipments.map { it.id!! }.toSet()


fun toReservationResponse(reservation: Reservation): ReservationResponse =
    ReservationResponse(
        id = reservation.id!!,
        title = reservation.title,
        date = reservation.date,
        startTime = reservation.startTime,
        endTime = reservation.endTime,
        classroom = toClassroomSummary(reservation.classroom),
        type = reservation.type,
        description = reservation.description,
        numberOfParticipants = reservation.numberOfParticipants
    )


fun toModel3dResponse(filePath: String): Model3dResponse =
    Model3dResponse(filePath = filePath)