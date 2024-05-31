package inc.evil.service


import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.persistance.repositories.ClassroomRepository
import org.koin.core.component.KoinComponent
import java.util.*

class ClassroomService(private val classroomRepository: ClassroomRepository) : KoinComponent {

    fun getAll(): List<ClassroomSummaryDto> {
//        return classroomRepository.findAll()
        TODO("Not implemented yet")
    }

    fun createClassroom(classroomDto: ClassroomPostDto): ClassroomPostDto {
        TODO("Not implemented yet")
    }

    fun getClassroomById(classroomId: UUID): ClassroomSummaryDto? {
        TODO("Not implemented yet")
    }

    fun deleteClassroomById(classroomId: UUID) {
        TODO("Not implemented yet")
    }
}
