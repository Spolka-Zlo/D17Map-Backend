package inc.evil.d17map.mappers

import inc.evil.d17map.dtos.*
import inc.evil.d17map.entities.*
import org.springframework.security.core.GrantedAuthority
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
        modelKey = classroom.modelKey,
        id = classroom.id!!,
        equipmentIds = toEquipmentIds(classroom.equipments),
        floorName = classroom.floor.name,
    )
}

fun toExtraRoomResponse(extraRoom: ExtraRoom): ExtraRoomResponse =
    ExtraRoomResponse(
        id = extraRoom.id!!,
        name = extraRoom.name,
        modelKey = extraRoom.modelKey,
        description = extraRoom.description,
        type = extraRoom.type,
        floorName = extraRoom.floor.name,
    )

fun toClassroomSummary(classroom: Classroom): ClassroomSummary =
    ClassroomSummary(
        id = classroom.id!!,
        name = classroom.name,
        modelKey = classroom.modelKey,
        capacity = classroom.capacity
    )


fun toEquipmentIds(equipments: Set<Equipment>): Set<UUID> = equipments.map { it.id!! }.toSet()


fun toReservationResponse(reservation: Reservation): ReservationResponse {
    return ReservationResponse(
        id = reservation.id!!,
        title = reservation.title,
        date = reservation.date,
        startTime = reservation.startTime,
        endTime = reservation.endTime,
        classroom = toClassroomSummary(reservation.classroom),
        type = reservation.type,
        description = reservation.description,
        numberOfParticipants = reservation.numberOfParticipants,
        recurringId = reservation.recurringId,
        recurringType = reservation.recurringType,
        recurringEndDate = reservation.recurringEndDate
    )
}


fun toRoleResponse(authorities: Collection<GrantedAuthority>) =
    authorities
        .map { it.authority }
        .filter { it.startsWith("ROLE_") }
        .map { it.substring(5) }


fun toFloorResponse(floor: Floor): FloorResponse {
    return FloorResponse(
        id = floor.id!!,
        floorName = floor.name,
    )
}