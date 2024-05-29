package inc.evil.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object Classroom : UUIDTable() {
    val description = text("description")
}

object ClassroomDetails : UUIDTable() {
    val classroomId = reference("classroom_id", Classroom.id).uniqueIndex()
    val numberOfSeats = integer("number_of_seats")
    val equipment = text("equipment")
}
