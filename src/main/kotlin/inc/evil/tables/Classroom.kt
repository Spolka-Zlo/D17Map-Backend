package inc.evil.tables

import org.jetbrains.exposed.sql.Table

object Classroom : Table() {
    val id = uuid("id").uniqueIndex()
    val description = text("description")
    val details = text("details")
}

object ClassroomDetails : Table() {
    val classroomId = reference("classroom_id", Classroom.id).uniqueIndex()
    val numberOfSeats = integer("numberOfSeats")
    val equipment = text("equipment")
}