package inc.evil.d17map.services

import inc.evil.d17map.dtos.EquipmentDto
import inc.evil.d17map.entities.Equipment
import inc.evil.d17map.findOne
import inc.evil.d17map.repositories.EquipmentRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class EquipmentService(val equipmentRepository: EquipmentRepository) {
    fun createEquipment(equipmentDto: EquipmentDto): EquipmentDto {
        val equipment = Equipment(name = equipmentDto.name)
        val createdEquipment = equipmentRepository.save(equipment)

        return EquipmentDto(
            id = createdEquipment.id!!,
            name = createdEquipment.name
        )
    }

    fun removeEquipment(equipmentId: UUID) {
        val equipment = equipmentRepository.findOne(equipmentId)!!
        equipmentRepository.delete(equipment)
    }

    fun getAll(): List<EquipmentDto> {
        val equipments = equipmentRepository.findAll()

        return equipments.map { equipment ->
            EquipmentDto(
                id = equipment.id!!,
                name = equipment.name
            )
        }
    }
}
