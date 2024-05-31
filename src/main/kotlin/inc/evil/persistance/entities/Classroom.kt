package inc.evil.persistance.entities

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*


object Classrooms : UUIDTable() {
    val name = text("name")
    val description = text("description")
    val numberOfSeats = integer("numberOfSeats")
}


class Classroom(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Classroom>(Classrooms)
    var name by Classrooms.name
    var description by Classrooms.description
    var numberOfSeats by Classrooms.numberOfSeats
    //val equipment by Equipment referencedOn Equipments.classroom
}

