package inc.evil.tables

import inc.evil.enums.ReservationType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

object Reservations : UUIDTable() {
    val userId = reference("user_id", id)
    val classroomId = reference("classroom_id", id)
    val name = text("name")
    val date = date("date")
    val startTime = datetime("start_time")
    val endTime = datetime("end_time")
    val type = enumerationByName("type", 10, ReservationType::class)
}

class Reservation(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Reservation>(Reservations)

    var userId by Reservations.userId
    var classroomId by Reservations.classroomId
    var name by Reservations.name
    var date by Reservations.date
    var startTime by Reservations.startTime
    var endTime by Reservations.endTime
    var type by Reservations.type
}