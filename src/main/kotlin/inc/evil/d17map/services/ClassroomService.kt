package inc.evil.d17map.services

import inc.evil.d17map.dtos.ClassroomDto
import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toClassroomDto
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.EquipmentRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClassroomService(
    private val classroomRepository: ClassroomRepository,
    private val equipmentRepository: EquipmentRepository
) {
    fun getAll(): List<ClassroomDto> {
        val classrooms = classroomRepository.findAll()
        return classrooms.map { classroomDto ->
            toClassroomDto(classroomDto)
        }
    }

    fun createClassroom(classroomDto: ClassroomRequest): ClassroomDto {
        val equipments = equipmentRepository.findAllById(classroomDto.equipmentIds!!) // TODO WTF
        val classroom = Classroom(
            name = classroomDto.name,
            description = classroomDto.description,
            capacity = classroomDto.capacity,
            equipments = equipments.toMutableSet()
        )
        val savedClassroomDto = classroomRepository.save(classroom)
        return toClassroomDto(savedClassroomDto)
    }

    fun findById(id: UUID): ClassroomDto {
        val classroomDto = classroomRepository.findOne(id)
        return toClassroomDto(classroomDto)
    }

    fun deleteById(id: UUID) {
        if (!classroomRepository.existsById(id)) {
            throw EntityNotFoundException("Classroom with id '$id' not found")
        }
        classroomRepository.deleteById(id)
    }

    fun updateClassroom(id: UUID, classroomDto: ClassroomDto): ClassroomDto {
        val classroom = classroomRepository.findOne(id)
        val equipments = equipmentRepository.findAllById(classroomDto.equipmentIds!!)

        classroom?.name = classroomDto.name
        classroom?.description = classroomDto.description
        classroom?.capacity = classroomDto.capacity
        classroom?.equipments = equipments.toMutableSet()

        val updatedClassroomDto = classroom?.let { classroomRepository.save(it) }
        return toClassroomDto(updatedClassroomDto)
    }
}