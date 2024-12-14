package inc.evil.d17map.repositories

import inc.evil.d17map.entities.ExtraRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExtraRoomRepository : JpaRepository<ExtraRoom, UUID> {

    @Query("""
        SELECT e FROM ExtraRoom e
        WHERE e.floor.name = :floorName
        AND e.floor.building.name = :buildingName
    """)
    fun findByBuildingAndFloor(buildingName: String, floorName: String): List<ExtraRoom>

    @Query("""
        SELECT e FROM ExtraRoom e
        WHERE e.floor.building.name = :buildingName
    """)
    fun findByBuilding(buildingName: String): List<ExtraRoom>

    @Query("""
        SELECT e FROM ExtraRoom e
        WHERE e.id = :id
        AND e.floor.name = :floorName
        AND e.floor.building.name = :buildingName
    """)
    fun findByIdAndBuildingAndFloor(id: UUID, buildingName: String, floorName: String): ExtraRoom?
}
