package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Floor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FloorRepository : JpaRepository<Floor, UUID> {
    fun findByBuildingName(buildingName: String): List<Floor>
    fun findByNameAndBuildingName(name: String, buildingName: String): Floor?
}