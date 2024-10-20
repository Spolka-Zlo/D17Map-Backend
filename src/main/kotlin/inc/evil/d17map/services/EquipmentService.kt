package inc.evil.d17map.services

import inc.evil.d17map.dtos.EquipmentRequest
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.entities.Equipment
import inc.evil.d17map.findOne
import inc.evil.d17map.repositories.EquipmentRepository
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class EquipmentService(val equipmentRepository: EquipmentRepository) {
    fun createEquipment(equipmentDto: EquipmentRequest): EquipmentResponse {
        val equipment = Equipment(name = equipmentDto.name)
        val createdEquipment = equipmentRepository.save(equipment)

        return EquipmentResponse(
            id = createdEquipment.id!!,
            name = createdEquipment.name
        )
    }

    fun removeEquipment(equipmentId: UUID) {
        val equipment = equipmentRepository.findOne(equipmentId)!!
        equipmentRepository.delete(equipment)
    }

    fun createEquipmentBatch(equipmentNames: List<String>): List<UUID> {
        val equipments = equipmentNames.map { name -> Equipment(name = name) }
        equipmentRepository.saveAll(equipments)
        return equipments.map { it.id!! }
    }

    fun getAll(): List<EquipmentResponse> {
        val equipments = equipmentRepository.findAll()

        return equipments.map { equipment ->
            EquipmentResponse(
                id = equipment.id!!,
                name = equipment.name
            )
        }
    }
}
