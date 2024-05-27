package inc.evil.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table

object Classroom : UUIDTable() {
    val description = text("description")
    val details = text("details")
}

object ClassroomDetails : Table() {
    val classroomId = reference("classroom_id", Classroom.id).uniqueIndex()
    val numberOfSeats = integer("numberOfSeats")
    val equipment = text("equipment")
}