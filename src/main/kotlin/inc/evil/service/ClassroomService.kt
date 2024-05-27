package inc.evil.service

import inc.evil.dao.ClassroomDAO
import inc.evil.dto.ClassroomFullDto
import inc.evil.dto.ClassroomSummaryDto
import org.koin.core.component.KoinComponent
import java.util.*


class ClassroomService(private val classroomDAO: ClassroomDAO) : KoinComponent {
    fun getAll(): List<ClassroomSummaryDto> {
        val classrooms = classroomDAO.getAllClassrooms()


        // TODO introduce mapper
        val classroomsDtoList = ArrayList<ClassroomSummaryDto>()


        classrooms.forEach { classroom ->
            val classroomDto = ClassroomSummaryDto(classroom.id, classroom.description)
            classroomsDtoList.add(classroomDto)
        }

        return classroomsDtoList

    }

    fun getByID(id: UUID): ClassroomFullDto? {
        val classroom = classroomDAO.getClassroomById(id)
        val classroomDetails = classroomDAO.getClassroomDetails(id)

        return null
    }

    fun getByName(name :String) : ClassroomFullDto? {
        return null
    }

    fun post(dto: ClassroomFullDto) : ClassroomSummaryDto? {
        // TODO create new entity
        return null
    }

    fun patch(dto: ClassroomFullDto /*TODO change type for patch*/): Unit? {
        return null
    }


}