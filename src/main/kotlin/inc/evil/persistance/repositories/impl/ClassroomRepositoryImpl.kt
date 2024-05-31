package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Classroom
import inc.evil.persistance.repositories.ClassroomRepository
import java.util.*

class ClassroomRepositoryImpl: BaseRepository<UUID, Classroom>(Classroom), ClassroomRepository {
}