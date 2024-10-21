package inc.evil.d17map.runners

import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Equipment
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.EquipmentRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataLoader(
    private val equipmentRepository: EquipmentRepository,
    private val classroomRepository: ClassroomRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val equipmentIds = listOf(
            createAndSaveEquipment("7f000101-92aa-105c-8192-aa5ba5510001", "COMPUTERS"),
            createAndSaveEquipment("7f000101-92aa-105c-8192-aa5c05120002", "ROUTERS"),
            createAndSaveEquipment("7f000101-92aa-105c-8192-aa68147a0003", "PROJECTOR"),
            createAndSaveEquipment("7f000101-92aa-105c-8192-aa68147a0004", "BOARD")
        )

        val classrooms = listOf(
            createClassroom("7f000101-92aa-105c-8192-aa69c7e70004", "2.41", "fajna sala na egzaminy, dużo się tu dzieje", 100, equipmentIds[2]),
            createClassroom("7f000101-92aa-105c-8192-aa6aae9a0005", "4.27", "sieci sieci sieci i inne takie fajne", 15, equipmentIds[1]),
            createClassroom("7f000101-92aa-105c-8192-aa6ba0d30006", "3.31", "obiektowe zwierzaki ewoluują w tej sali", 20, equipmentIds[0])
        )
        classroomRepository.saveAll(classrooms)
    }

    private fun createAndSaveEquipment(id: String, name: String): Equipment {
        val equipment = Equipment(
            id = UUID.fromString(id),
            name = name
        )
        return equipmentRepository.save(equipment)
    }

    private fun createClassroom(id: String, name: String, description: String, capacity: Int, equipment: Equipment): Classroom {
        return Classroom(
            id = UUID.fromString(id),
            name = name,
            description = description,
            capacity = capacity,
            equipments = mutableSetOf(equipment)
        )
    }
}
