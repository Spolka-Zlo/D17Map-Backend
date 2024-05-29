package inc.evil.service


import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.ClassroomSummaryDto
import org.koin.core.component.KoinComponent
import java.util.*

class ClassroomService(

) : KoinComponent {

    fun getAll(): List<ClassroomSummaryDto> {
//        val classrooms = classroomDAO.getAllClassrooms()
//        return classrooms.map { classroomMapper.toSummaryDto(it) }
        return listOf()
    }

    fun createClassroom(classroomDto: ClassroomPostDto) {
//        val classroomEntity = classroomMapper.toEntity(classroomDto)
//        classroomDAO.createClassroom(classroomEntity)
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
