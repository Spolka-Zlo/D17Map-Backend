package inc.evil.tables

import inc.evil.enums.ReservationType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime

object Reservation : UUIDTable() {
    val userId = reference("user_id", id)
    val classroomId = reference("classroom_id", id)
    val name = text("name")
    val date = date("date")
    val startTime = datetime("start_time")
    val endTime = datetime("end_time")
    val type = enumerationByName("type", 10, ReservationType::class)
}