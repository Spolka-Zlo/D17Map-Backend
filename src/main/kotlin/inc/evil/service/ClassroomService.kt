package inc.evil.service

import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.persistance.entities.Classroom
import inc.evil.persistance.entities.Equipment
import inc.evil.persistance.entities.Equipments
import inc.evil.persistance.repositories.ClassroomRepository
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ClassroomService(private val classroomRepository: ClassroomRepository) {
    fun getAll(): List<ClassroomSummaryDto> = transaction {

        val classrooms = Classroom.all().toList()


        val classroomsDto = classrooms.map { classroom ->
            ClassroomSummaryDto(classroom.id.value, classroom.name, classroom.capacity, classroom.equipments.map {
                it.name
            })
        }

        classroomsDto
    }

    fun createClassroom(classroomPostDto: ClassroomPostDto): ClassroomSummaryDto = transaction {

        // Pobieramy wszystkie pasujące equipmenty
        val dbEquipments: List<Equipment> = classroomPostDto.equipment.mapNotNull { eqName ->
            Equipment.find { Equipments.name eq eqName }.firstOrNull()
        }

        // Tworzymy classroom i dodajemy do niego pobrane equipmenty
        val classroomEntity: Classroom = Classroom.new {
            name = classroomPostDto.name
            description = classroomPostDto.description
            capacity = classroomPostDto.capacity
            equipments = SizedCollection(dbEquipments)
        }

        // aktualizujemy drugą stronę relacji
        dbEquipments.forEach { equipment ->
            equipment.classroms + (classroomEntity)
        }

        // Tworzymy DTO w TRANSAKCJI, bo tak trzeba (chyba)
        ClassroomSummaryDto(
            id = classroomEntity.id.value,
            name = classroomEntity.name,
            capacity = classroomEntity.capacity,
            equipment = classroomEntity.equipments
                .map { equipment -> equipment.name }
        )
    }

    fun findByName(name: String): List<ClassroomSummaryDto> {
        TODO("not yet implemented")

    }

    fun findById(id: UUID): ClassroomSummaryDto? {
        TODO("not yet implemented")

    }

    fun deleteById(id: UUID) {
        TODO("not yet implemented")

    }

    fun updateClassroom(id: UUID, classroomPostDto: ClassroomPostDto): ClassroomSummaryDto? {
        TODO("not yet implemented")
    }
}

