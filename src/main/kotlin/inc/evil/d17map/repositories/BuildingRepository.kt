package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Building
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BuildingRepository : JpaRepository<Building, UUID>
