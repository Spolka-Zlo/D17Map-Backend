package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Equipment
import inc.evil.persistance.entities.Equipments
import inc.evil.persistance.repositories.EquipmentRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class EquipmentRepositoryImpl : BaseRepository<UUID, Equipment>(Equipment), EquipmentRepository {
    override fun findByName(name: String): List<Equipment> = transaction {
        Equipment.find { Equipments.name eq name }.toList()
    }
}
