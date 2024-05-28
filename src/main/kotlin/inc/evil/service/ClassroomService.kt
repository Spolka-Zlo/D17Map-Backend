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

    // returns all classrooms without description
    fun getAll(): List<ClassroomSummaryDto> {
        throw NotImplementedError()
    }




}