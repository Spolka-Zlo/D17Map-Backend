package inc.evil.persistance.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Equipments : UUIDTable() {
    val name = text("name").uniqueIndex()
}

class Equipment(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Equipment>(Equipments)
    var name by Equipments.name
    var classroms by Classroom via ClassroomsEquipments
}

