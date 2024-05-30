package inc.evil.service


import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.tables.Classroom
import inc.evil.tables.Equipment
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.*

class ClassroomService(

) : KoinComponent {

    fun getAll(): List<ClassroomSummaryDto> {


//        val classrooms = classroomDAO.getAllClassrooms()
//        return classrooms.map { classroomMapper.toSummaryDto(it) }
        return listOf()
    }

    fun createClassroom(classroomDto: ClassroomPostDto): ClassroomPostDto {


        transaction {
            val classroomO = Classroom.new {
                name = classroomDto.name
                description = classroomDto.description
                numberOfSeats = classroomDto.capacity

            }

            classroomDto.equipment.forEach { equipment ->
                Equipment.new {
                    name = equipment
                    classroom = classroomO
                }
            }

        }

    }

    fun getClassroomById(classroomId: UUID): ClassroomSummaryDto? {
//        val classroomEntity = classroomDAO.getClassroomById(classroomId) ?: return null
//        return classroomMapper.toSummaryDto(classroomEntity)
        return null
    }

    fun deleteClassroomById(classroomId: UUID) {
//        classroomDAO.deleteClassroom(classroomId)
    }
}
