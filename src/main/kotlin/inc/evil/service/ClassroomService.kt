package inc.evil.service

import inc.evil.dao.ClassroomDAO
import inc.evil.dto.ClassroomFullDto
import inc.evil.dto.ClassroomSummaryDto
import inc.evil.mapper.ClassroomMapper
import org.koin.core.component.KoinComponent
import java.util.*


class ClassroomService(
    private val classroomDAO: ClassroomDAO,
    private val classroomMapper: ClassroomMapper,
) : KoinComponent {
    fun getAll(): List<ClassroomSummaryDto> {
        val classrooms = classroomDAO.getAllClassrooms()

        // TODO improve mapper
        val classroomsDtoList = ArrayList<ClassroomSummaryDto>()


        classrooms.forEach { classroom ->
            val classroomDto = classroomMapper.toClassroomSummaryDto(classroom)
            classroomsDtoList.add(classroomDto)
        }

        return classroomsDtoList

    }

    fun getByID(id: UUID): ClassroomFullDto? {
        // TODO fix nulls...
        val classroom = classroomDAO.getClassroomById(id) ?: return null
        val classroomDetails = classroomDAO.getClassroomDetails(id) ?: return null

        val dto = classroomMapper.toFullDto(classroom, classroomDetails)
        return dto
    }

    fun getByName(name :String) : ClassroomFullDto? {
//        // TODO fix nulls...
//        val classroom = classroomDAO.get) ?: return null
//        val classroomDetails = classroomDAO.getClassroomDetails(id) ?: return null
//
//        val dto = classroomMapper.toFullDto(classroom, classroomDetails)
//        return dto

        return null
    }

    fun post(dto: ClassroomFullDto) : ClassroomSummaryDto? {

        val entity = classroomMapper.fromFullDto(dto)
       // classroomDAO.createClassroom(entity)
        return null
    }

    fun patch(dto: ClassroomFullDto /*TODO change type for patch*/): Unit? {
        return null
    }

    fun delete(id: UUID) {
        classroomDAO.deleteClassroom(id)
    }


}