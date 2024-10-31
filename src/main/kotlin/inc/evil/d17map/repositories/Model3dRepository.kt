package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Model3d
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface Model3dRepository : JpaRepository<Model3d, UUID> {
    fun findByBuildingAndFloor(building: String, floor: Int): Optional<Model3d>
}
