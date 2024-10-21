package inc.evil.d17map.services

import inc.evil.d17map.EquipmentNotFoundException
import inc.evil.d17map.dtos.EquipmentRequest
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.entities.Equipment
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toEquipmentResponse
import inc.evil.d17map.repositories.EquipmentRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class EquipmentService(val equipmentRepository: EquipmentRepository) {
    fun createEquipment(equipmentRequest: EquipmentRequest): EquipmentResponse {
        val equipment = Equipment(name = equipmentRequest.name)
        val createdEquipment = equipmentRepository.save(equipment)

        return toEquipmentResponse(createdEquipment)
    }

    fun removeEquipment(equipmentId: UUID) {
        if (!equipmentRepository.existsById(equipmentId)) {
            throw EquipmentNotFoundException(equipmentId)
        }
        val equipment = equipmentRepository.findOne(equipmentId)!!
        equipmentRepository.delete(equipment)
    }

    fun getAll(): List<EquipmentResponse> {
        val equipments = equipmentRepository.findAll()

        return equipments.map {
            toEquipmentResponse(it)
        }
    }
}
