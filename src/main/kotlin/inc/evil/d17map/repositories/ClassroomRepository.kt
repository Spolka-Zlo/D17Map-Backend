package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Floor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Repository
interface ClassroomRepository : JpaRepository<Classroom, UUID> {

    @Query("""
        SELECT c FROM Classroom c 
        WHERE c.floor = :floor 
        AND c.capacity >= :peopleCount 
        AND NOT EXISTS (
            SELECT r FROM Reservation r 
            WHERE r.classroom = c 
            AND r.date = :date 
            AND ((r.startTime <= :endTime AND r.endTime >= :startTime))
        )
    """)
    fun findAvailableClassrooms(
        @Param("floor") floor: Floor,
        @Param("date") date: LocalDate,
        @Param("startTime") startTime: LocalTime,
        @Param("endTime") endTime: LocalTime,
        @Param("peopleCount") peopleCount: Int
    ): List<Classroom>

    fun findByFloor(@Param("floor") floor: Floor): List<Classroom>

    fun findByIdAndFloor(@Param("id") id: UUID, @Param("floor") floor: Floor): Classroom?

    fun existsByIdAndFloor(@Param("id") id: UUID, @Param("floor") floor: Floor): Boolean

    @Query("""
    SELECT c FROM Classroom c
    WHERE c.floor.building.name = :buildingName
    """)
    fun findAllByBuildingName(buildingName: String): List<Classroom>

}
