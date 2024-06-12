package inc.evil.persistance.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import java.util.*


object Classrooms : UUIDTable() {
    val name = text("name").uniqueIndex()
    val description = text("description")
    val capacity = integer("capacity")
}


object ClassroomsEquipments : Table() {
    private val classroom = reference("classroom", Classrooms)
    private val equipment = reference("equipment", Equipments)
    override val primaryKey = PrimaryKey(
        classroom, equipment,
        name = "PK_classroom_equipment")
}



class Classroom(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Classroom>(Classrooms)
    var name by Classrooms.name
    var description by Classrooms.description
    var capacity by Classrooms.capacity
    var equipments by Equipment via ClassroomsEquipments
    val reservations by Reservation referrersOn Reservations.classroom
}

