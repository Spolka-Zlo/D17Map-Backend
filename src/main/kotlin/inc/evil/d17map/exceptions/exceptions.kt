package inc.evil.d17map.exceptions

import java.util.*
import java.lang.RuntimeException


// NOT FOUND

class ClassroomNotFoundException(
    id: UUID
) : RuntimeException("Classroom with id '$id' not found")

class EquipmentNotFoundException(
    id: UUID
) : RuntimeException("Equipment with id '$id' not found")

class ReservationNotFoundException(
    id: UUID
) : RuntimeException("Reservation with id '$id' not found")

class RecurringReservationNotFoundException(
    id: UUID
) : RuntimeException("Recurring Reservation with recurring id '$id' not found")

class UserNotFoundException : RuntimeException {
    constructor(id: UUID) : super("User with id '$id' not found")
    constructor(email: String) : super("User with email '$email' not found")
}

class RoleNotFoundException(roleName: String) : RuntimeException("Role with name '$roleName' not found")


class BuildingNotFoundException(buildingName: String) : RuntimeException(
    "Building with name '$buildingName' not found."
)

// INVALID DATA

class InvalidReservationDataException(message: String) : RuntimeException(message)

class InvalidClassroomDataException(message: String) : RuntimeException(message)

class InvalidExtraRoomDataException(message: String) : RuntimeException(message)

class InvalidEquipmentDataException(message: String) : RuntimeException(message) {
    companion object {
        fun blankName(): InvalidEquipmentDataException {
            return InvalidEquipmentDataException("Invalid equipment data: name cannot be blank.")
        }

        fun emptyEquipmentList(): InvalidEquipmentDataException {
            return InvalidEquipmentDataException("Invalid equipment data: equipment list cannot be empty.")
        }
    }
}

// Missing parameters

class MissingParameterException(parameterName: String)
    : RuntimeException("Query parameter '$parameterName' must be specified")

class UserAlreadyExistsException(email: String) : RuntimeException("User with email '$email' already exists")
