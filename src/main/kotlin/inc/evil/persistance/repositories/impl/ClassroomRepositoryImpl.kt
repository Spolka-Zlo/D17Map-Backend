package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Classroom
import inc.evil.persistance.repositories.ClassroomRepository
import java.util.UUID

class ClassroomRepositoryImpl: BaseRepository<Classroom, UUID>(), ClassroomRepository {
}