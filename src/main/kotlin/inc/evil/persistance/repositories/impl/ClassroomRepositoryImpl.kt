package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Classroom
import inc.evil.persistance.entities.Classrooms
import inc.evil.persistance.repositories.ClassroomRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ClassroomRepositoryImpl : BaseRepository<UUID, Classroom>(Classroom), ClassroomRepository {
    override fun findByName(name: String): List<Classroom> = transaction {
        Classroom.find { Classrooms.name eq name }.toList()
    }
}
