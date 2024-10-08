package inc.evil.d17map.services

import inc.evil.d17map.dtos.EquipmentRequest
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.entities.Equipment
import inc.evil.d17map.findOne
import inc.evil.d17map.repositories.EquipmentRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class EquipmentService (val equipmentRepository : EquipmentRepository) {
    fun createEquipment(equipmentRequest: EquipmentRequest): EquipmentResponse {
        val equipment = Equipment(name = equipmentRequest.name)
        val createdEquipment = equipmentRepository.save(equipment)

        val equipmentResponse = EquipmentResponse(
            id = createdEquipment.id ?: throw IllegalStateException("ID should not be null after save"),
            name = createdEquipment.name
        )
        return equipmentResponse
    }

    fun removeEquipment(equipmentId: UUID) {
        equipmentRepository.findOne(equipmentId)?.let {
            equipmentRepository.delete(it)
        } ?: throw EntityNotFoundException("Equipment not found with id: $equipmentId")
    }
}