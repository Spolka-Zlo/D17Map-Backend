package inc.evil.d17map.exceptions

import java.util.*

// BASE CLASS FOR NOT FOUND EXCEPTIONS
open class NotFoundException(message: String) : RuntimeException(message)

// SPECIFIC NOT FOUND EXCEPTIONS
class ClassroomNotFoundException(id: UUID) : NotFoundException("Classroom with id '$id' not found")

class EquipmentNotFoundException(id: UUID) : NotFoundException("Equipment with id '$id' not found")

class ReservationNotFoundException(id: UUID) : NotFoundException("Reservation with id '$id' not found")

class UserNotFoundException(email: String) : NotFoundException("User with email '$email' not found")

class BuildingNotFoundException(buildingName: String) :
    NotFoundException("Building with name '$buildingName' not found.")

class RoleNotFoundException(roleName: String) : NotFoundException("Role with name '$roleName' not found")


// INVALID DATA

class InvalidReservationDataException(message: String) : RuntimeException(message)

class InvalidClassroomDataException(message: String) : RuntimeException(message)

class InvalidExtraRoomDataException(message: String) : RuntimeException(message)

// ALREADY EXISTS
class UserAlreadyExistsException(email: String) : RuntimeException("User with email '$email' already exists")

// CUSTOM SECURITY EXCEPTIONS
class JWTFilterException(message: String) : RuntimeException(message)

