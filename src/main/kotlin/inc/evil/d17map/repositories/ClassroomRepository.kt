package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Classroom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ClassroomRepository : JpaRepository<Classroom, UUID>