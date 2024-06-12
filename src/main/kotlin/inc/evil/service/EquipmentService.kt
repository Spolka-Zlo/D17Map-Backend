package inc.evil.service

import inc.evil.dto.EquipmentPostDto
import inc.evil.persistance.entities.Equipment
import org.jetbrains.exposed.sql.transactions.transaction

class EquipmentService {
    fun getAll() = transaction {
        Equipment.all().map {
            it.name
        }
    }

    fun createEquipment(equipmentPostDto: EquipmentPostDto) = transaction {
        val equipment: Equipment = Equipment.new {
            name = equipmentPostDto.name
        }
        EquipmentPostDto(equipment.id.value, equipment.name)
    }
}
