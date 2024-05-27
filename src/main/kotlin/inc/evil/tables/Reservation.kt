package inc.evil.tables

import inc.evil.enums.ReservationType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Reservation : UUIDTable() {
    val userId = reference("user_id", User.id)
    val classroomId = reference("classroom_id", Classroom.id)
    val name = text("name")
    val startDate = datetime("start_date")
    val endDate = datetime("end_date")
    val type = enumerationByName("type", 10, ReservationType::class)
}