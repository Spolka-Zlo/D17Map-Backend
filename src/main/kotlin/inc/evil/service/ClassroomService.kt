package inc.evil.service

import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.persistance.entities.Classroom
import inc.evil.persistance.repositories.ClassroomRepository
import java.util.*

class ClassroomService(private val classroomRepository: ClassroomRepository) {
    fun getAll(): List<ClassroomSummaryDto> {
        val classrooms = classroomRepository.findAll()
        return classrooms.map {
            ClassroomSummaryDto(
                id = it.id.value,
                name = it.name,
                capacity = it.numberOfSeats,
                equipment = listOf()
            )
        }
    }

    fun createClassroom(classroomPostDto: ClassroomPostDto): ClassroomSummaryDto {
        val classroom = Classroom.new {
            name = classroomPostDto.name
            description = classroomPostDto.description
            numberOfSeats = classroomPostDto.capacity
        }

        return ClassroomSummaryDto(
            id = classroom.id.value,
            name = classroom.name,
            capacity = classroom.numberOfSeats,
            equipment = classroomPostDto.equipment
        )
    }

    fun findByName(name: String): List<ClassroomSummaryDto> {
        val classrooms = classroomRepository.findByName(name)
        return classrooms.map {
            ClassroomSummaryDto(
                id = it.id.value,
                name = it.name,
                capacity = it.numberOfSeats,
                equipment = listOf()
            )
        }
    }

    fun findById(id: UUID): ClassroomSummaryDto? {
        val classroom = classroomRepository.findById(id)
        return classroom?.let {
            ClassroomSummaryDto(
                id = it.id.value,
                name = it.name,
                capacity = it.numberOfSeats,
                equipment = listOf()
            )
        }
    }

    fun deleteById(id: UUID) {
        return classroomRepository.deleteById(id)
    }

    fun updateClassroom(id: UUID, classroomPostDto: ClassroomPostDto): ClassroomSummaryDto? {
        val classroom = classroomRepository.findById(id) ?: return null
        classroom.apply {
            name = classroomPostDto.name
            description = classroomPostDto.description
            numberOfSeats = classroomPostDto.capacity
        }

        return ClassroomSummaryDto(
            id = classroom.id.value,
            name = classroom.name,
            capacity = classroom.numberOfSeats,
            equipment = classroomPostDto.equipment
        )
    }
}
