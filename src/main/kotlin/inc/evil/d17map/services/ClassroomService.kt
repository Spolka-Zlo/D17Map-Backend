package inc.evil.d17map.services

import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.exceptions.ClassroomNotFoundException
import inc.evil.d17map.mappers.toClassroomResponse
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.EquipmentRepository
import inc.evil.d17map.repositories.FloorRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class ClassroomService(
    private val classroomRepository: ClassroomRepository,
    private val equipmentRepository: EquipmentRepository,
    private val floorRepository: FloorRepository
) {
    fun getAll(): List<ClassroomResponse> {
        val classrooms = classroomRepository.findAll()
        return classrooms.map { toClassroomResponse(it) }
    }

    fun createClassroom(classroomRequest: ClassroomRequest): ClassroomResponse {
        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)
        val floor = floorRepository.findById(classroomRequest.floorId)
        val classroom = Classroom(
            name = classroomRequest.name,
            description = classroomRequest.description,
            capacity = classroomRequest.capacity,
            modelKey = classroomRequest.modelKey,
            equipments = equipments.toMutableSet(),
            floor = floor.orElseThrow { EntityNotFoundException("Floor with id ${classroomRequest.floorId} not found") },
            photo = classroomRequest.photo
        )
        val savedClassroomDto = classroomRepository.save(classroom)
        return toClassroomResponse(savedClassroomDto)
    }

    fun findAvailableClassrooms(date: LocalDate, timeRange: String, peopleCount: Int): List<ClassroomResponse> {
        val (start, end) = timeRange.split("-")
        val (startTime, endTime) = Pair(LocalTime.parse(start), LocalTime.parse(end))

        val classrooms = classroomRepository.findAvailableClassrooms(date, startTime, endTime, peopleCount)
        return classrooms.map { toClassroomResponse(it) }
    }

    fun updateClassroom(id: UUID, classroomRequest: ClassroomRequest): ClassroomResponse {
        val classroom = classroomRepository.findById(id)
            .orElseThrow { ClassroomNotFoundException(id) }

        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)
        classroom.name = classroomRequest.name
        classroom.description = classroomRequest.description
        classroom.capacity = classroomRequest.capacity
        classroom.equipments = equipments.toMutableSet()

        val updatedClassroom = classroomRepository.save(classroom)
        return toClassroomResponse(updatedClassroom)
    }

    fun getClassroomPhotoById(id: UUID): ByteArray? {
        val classroom = classroomRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Classroom with id $id not found") }
        return classroom.photo
    }

    fun deleteById(id: UUID) {
        if (!classroomRepository.existsById(id)) {
            throw ClassroomNotFoundException(id)
        }
        classroomRepository.deleteById(id)
    }


// TODO uncomment and adjust when needed
//    fun findById(id: UUID): ClassroomResponse {
//        if (!classroomRepository.existsById(id)) {
//            throw ClassroomNotFoundException(id)
//        }
//        val classroomDto = classroomRepository.findOne(id)
//        return toClassroomResponse(classroomDto)
//    }
}