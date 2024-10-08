package inc.evil.d17map.services

import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.mappers.ClassroomMapper
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
    fun getAll(): List<ClassroomResponse> {
        val classrooms = classroomRepository.findAll()
        return classrooms.map { classroom ->
            ClassroomMapper.mapToClassroomResponse(classroom)
        }
    }

    fun createClassroom(classroomRequest: ClassroomRequest): ClassroomResponse {
        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)
        val classroom = Classroom(
            name = classroomRequest.name,
            description = classroomRequest.description,
            capacity = classroomRequest.capacity,
            equipments = equipments.toMutableSet()
        )
        val savedClassroom = classroomRepository.save(classroom)
        return ClassroomMapper.mapToClassroomResponse(savedClassroom)
    }

    fun findByName(name: String): ClassroomResponse {
        val classroom = classroomRepository.findAll().firstOrNull { it.name == name }
            ?: throw EntityNotFoundException("Classroom with name '$name' not found")
        return ClassroomMapper.mapToClassroomResponse(classroom)
    }

    fun findById(id: UUID): ClassroomResponse {
        val classroom = classroomRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Classroom with id '$id' not found") }
        return ClassroomMapper.mapToClassroomResponse(classroom)
    }

    fun deleteById(id: UUID) {
        if (!classroomRepository.existsById(id)) {
            throw EntityNotFoundException("Classroom with id '$id' not found")
        }
        classroomRepository.deleteById(id)
    }

    fun updateClassroom(id: UUID, classroomRequest: ClassroomRequest): ClassroomResponse {
        val classroom = classroomRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Classroom with id '$id' not found") }

        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)

        classroom.name = classroomRequest.name
        classroom.description = classroomRequest.description
        classroom.capacity = classroomRequest.capacity
        classroom.equipments = equipments.toMutableSet()

        val updatedClassroom = classroomRepository.save(classroom)
        return ClassroomMapper.mapToClassroomResponse(updatedClassroom)
    }
}

