package inc.evil.service

import inc.evil.dao.ClassroomDAO
import org.koin.core.component.KoinComponent


class ClassroomService(private val classroomDAO: ClassroomDAO) : KoinComponent {

}