package inc.evil.d17map.services

import inc.evil.d17map.ClassroomNotFoundException
import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toClassroomResponse
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.EquipmentRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClassroomService(
    private val classroomRepository: ClassroomRepository,
    private val equipmentRepository: EquipmentRepository
) {
    fun getAll(): List<ClassroomResponse> {
        val classrooms = classroomRepository.findAll()
        return classrooms.map { toClassroomResponse(it) }
    }

    fun createClassroom(classroomRequest: ClassroomRequest): ClassroomResponse {
        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)
        val classroom = Classroom(
            name = classroomRequest.name,
            description = classroomRequest.description,
            capacity = classroomRequest.capacity,
            equipments = equipments.toMutableSet()
        )
        val savedClassroomDto = classroomRepository.save(classroom)
        return toClassroomResponse(savedClassroomDto)
    }

// TODO uncomment and adjust when needed
//    fun findById(id: UUID): ClassroomResponse {
//        if (!classroomRepository.existsById(id)) {
//            throw ClassroomNotFoundException(id)
//        }
//        val classroomDto = classroomRepository.findOne(id)
//        return toClassroomResponse(classroomDto)
//    }
//    fun deleteById(id: UUID) {
//        if (!classroomRepository.existsById(id)) {
//            throw ClassroomNotFoundException(id)
//        }
//        classroomRepository.deleteById(id)
//    }
//    fun updateClassroom(id: UUID, classroomResponse: ClassroomResponse): ClassroomResponse {
//        if (!classroomRepository.existsById(id)) {
//            throw ClassroomNotFoundException(id)
//        }
//        val classroom = classroomRepository.findOne(id)
//        val equipments = equipmentRepository.findAllById(classroomResponse.equipmentIds)
//        classroom?.name = classroomResponse.name
//        classroom?.description = classroomResponse.description
//        classroom?.capacity = classroomResponse.capacity
//        classroom?.equipments = equipments.toMutableSet()
//        val updatedClassroomDto = classroom?.let { classroomRepository.save(it) }
//        return toClassroomResponse(updatedClassroomDto)
//    }
}