package inc.evil.persistance.entities

import inc.evil.enums.ReservationType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import java.util.*

object Reservations : UUIDTable() {
    val user = reference("user", Users)
    val classroom = reference("classroom", Classrooms)
    val title = text("title")
    val date = date("date")
    val startTime = time("start_time")
    val endTime = time("end_time")
    val type = enumerationByName("type", 10, ReservationType::class)
}

class Reservation(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Reservation>(Reservations)
    var user by User referencedOn Reservations.user
    var classroom by Classroom referencedOn Reservations.classroom
    var title by Reservations.title
    var date by Reservations.date
    var startTime by Reservations.startTime
    var endTime by Reservations.endTime
    var type by Reservations.type
}