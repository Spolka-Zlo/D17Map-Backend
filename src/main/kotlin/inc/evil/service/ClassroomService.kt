package inc.evil.service

import inc.evil.dao.ClassroomDAO
import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.mapper.ClassroomMapper
import inc.evil.plugins.UserService.Users.uuid
import org.koin.core.component.KoinComponent
import java.util.*

class ClassroomService(
    private val classroomDAO: ClassroomDAO,
    private val classroomMapper: ClassroomMapper,
) : KoinComponent {

    fun getAll(): List<ClassroomSummaryDto> {
        val classrooms = classroomDAO.getAllClassrooms()
        return classrooms.map { classroomMapper.toSummaryDto(it) }
    }

    fun createClassroom(classroomDto: ClassroomPostDto) {
        val classroomEntity = classroomMapper.toEntity(classroomDto)
        classroomDAO.createClassroom(classroomEntity)
    }

    fun getClassroomById(classroomId: UUID): ClassroomSummaryDto? {
        val classroomEntity = classroomDAO.getClassroomById(classroomId) ?: return null
        return classroomMapper.toSummaryDto(classroomEntity)
    }

    fun deleteClassroomById(classroomId: UUID) {
        classroomDAO.deleteClassroom(classroomId)
    }
}
