package inc.evil.persistance.repositories

import inc.evil.persistance.entities.Classroom
import java.util.UUID

interface ClassroomRepository : Repository<UUID, Classroom> {
    fun findByName(name: String): List<Classroom>
}
