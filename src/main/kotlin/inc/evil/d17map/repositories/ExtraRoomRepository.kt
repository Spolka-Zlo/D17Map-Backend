package inc.evil.d17map.repositories

import inc.evil.d17map.entities.ExtraRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExtraRoomRepository : JpaRepository<ExtraRoom, UUID>