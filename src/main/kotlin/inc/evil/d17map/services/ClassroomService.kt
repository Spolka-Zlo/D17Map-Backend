package inc.evil.d17map.services

import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Photo
import inc.evil.d17map.exceptions.ClassroomNotFoundException
import inc.evil.d17map.exceptions.InvalidClassroomDataException
import inc.evil.d17map.findOne
import inc.evil.d17map.mappers.toClassroomResponse
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.EquipmentRepository
import inc.evil.d17map.repositories.FloorRepository
import inc.evil.d17map.repositories.PhotoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class ClassroomService(
    private val classroomRepository: ClassroomRepository,
    private val equipmentRepository: EquipmentRepository,
    private val floorRepository: FloorRepository,
    private val photoRepository: PhotoRepository
) {
    fun getByBuildingAndFloor(buildingName: String, floorName: String): List<ClassroomResponse> {
        val floor = floorRepository.findByNameAndBuildingName(floorName, buildingName)
            ?: throw InvalidClassroomDataException("Floor '$floorName' not found in building '$buildingName'")
        val classrooms = classroomRepository.findByFloor(floor)
        return classrooms.map { toClassroomResponse(it) }
    }

    fun createClassroom(
        buildingName: String,
        floorName: String,
        classroomRequest: ClassroomRequest
    ): ClassroomResponse {
        val floor = floorRepository.findByNameAndBuildingName(floorName, buildingName)
            ?: throw InvalidClassroomDataException("Floor '$floorName' not found in building '$buildingName'")
        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)
        val photo = classroomRequest.photo
        val photoId = photo?.let { photoRepository.save(Photo(data = it)).id }
        val classroom = Classroom(
            name = classroomRequest.name,
            description = classroomRequest.description,
            capacity = classroomRequest.capacity,
            modelKey = classroomRequest.modelKey,
            equipments = equipments.toMutableSet(),
            floor = floor,
            photoId = photoId
        )
        val savedClassroom = classroomRepository.save(classroom)
        return toClassroomResponse(savedClassroom)
    }

    fun findAvailableClassrooms(
        buildingName: String,
        date: LocalDate,
        timeRange: String,
        peopleCount: Int
    ): List<ClassroomResponse> {
        val (start, end) = timeRange.split("-")
        val (startTime, endTime) = Pair(LocalTime.parse(start), LocalTime.parse(end))
        val classrooms = classroomRepository.findAvailableClassrooms(date, startTime, endTime, peopleCount)
        return classrooms.map { toClassroomResponse(it) }
    }

    fun updateClassroom(
        buildingName: String,
        floorName: String,
        id: UUID,
        classroomRequest: ClassroomRequest
    ): ClassroomResponse {
        val floor = floorRepository.findByNameAndBuildingName(floorName, buildingName)
            ?: throw InvalidClassroomDataException("Floor '$floorName' not found in building '$buildingName'")
        val classroom = classroomRepository.findByIdAndFloor(id, floor)
            ?: throw ClassroomNotFoundException(id)
        val equipments = equipmentRepository.findAllById(classroomRequest.equipmentIds)
        classroom.name = classroomRequest.name
        classroom.description = classroomRequest.description
        classroom.capacity = classroomRequest.capacity
        classroom.equipments = equipments.toMutableSet()
        val updatedClassroom = classroomRepository.save(classroom)
        return toClassroomResponse(updatedClassroom)
    }

    @Transactional(readOnly = true)
    fun getClassroomPhotoById(buildingName: String, id: UUID): ByteArray? {
        val classroom = classroomRepository.findById(id)
            .orElseThrow { ClassroomNotFoundException(id) }
        val photoId = classroom.photoId
        return photoRepository.findOne(photoId!!)?.data ?: throw ClassroomNotFoundException(id)
    }

    fun deleteByBuildingAndFloor(buildingName: String, id: UUID) {
        if (!classroomRepository.existsById(id)) {
            throw ClassroomNotFoundException(id)
        }
        classroomRepository.deleteById(id)
    }

    fun getAllClassroomsByBuilding(buildingName: String): List<ClassroomResponse> {
        val classrooms = classroomRepository.findAllByBuildingName(buildingName)
        return classrooms.map { toClassroomResponse(it) }
    }

}