package inc.evil.d17map.repositories

import inc.evil.d17map.entities.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<Reservation, UUID> {


    @Query("SELECT r FROM Reservation r WHERE r.date = :date AND r.classroom.floor.building.name = :buildingName ORDER BY r.date, r.startTime")
    fun findAllByDateAndBuildingName(
        @Param("date") date: LocalDate,
        @Param("buildingName") buildingName: String
    ): List<Reservation>

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.classroom.floor.building.name = :buildingName ORDER BY r.date, r.startTime")
    fun findAllByUserIdAndBuildingName(
        @Param("userId") userId: UUID,
        @Param("buildingName") buildingName: String
    ): List<Reservation>

    @Query("SELECT r FROM Reservation r WHERE r.date BETWEEN :startDate AND :endDate " +
            "AND r.classroom.floor.building.name = :buildingName " +
            "ORDER BY r.date, r.startTime")
    fun findAllByDateBetweenAndBuildingName(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate,
        @Param("buildingName") buildingName: String
    ): List<Reservation>

    @Query(
        "SELECT r " +
                "FROM Reservation r " +
                "WHERE r.user.id = :userID " +
                "AND r.date BETWEEN :startDate AND :endDate " +
                "AND r.classroom.floor.building.name = :buildingName " +
                "ORDER BY r.date, r.startTime"
    )
    fun findAllByUserAndDateBetweenAndBuilding(
        @Param("userID") userID: UUID,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate,
        @Param("buildingName") buildingName: String
    ): List<Reservation>

    @Query(
        "SELECT r " +
                "FROM Reservation r " +
                "WHERE r.user.id = :userID " +
                "AND r.classroom.floor.building.name = :buildingName " +
                "AND (r.date > :currentDate " +
                "OR (r.date = :currentDate AND r.startTime > :currentTime))" +
                "ORDER BY r.date, r.startTime"
    )
    fun findAllFuture(
        @Param("userID") userID: UUID,
        @Param("currentDate") currentDate: LocalDate,
        @Param("currentTime") currentTime: LocalTime,
        @Param("buildingName") buildingName: String
    ): List<Reservation>

    @Query(
        "SELECT r " +
                "FROM Reservation r " +
                "WHERE (r.date > :currentDate " +
                "OR (r.date = :currentDate AND r.endTime >= :currentTime)) " +
                "AND r.type = 'EVENT' " +
                "AND r.classroom.floor.building.name = :buildingName " +
                "ORDER BY r.date, r.startTime"
    )
    fun findAllCurrentOrFutureEvents(
        @Param("currentDate") currentDate: LocalDate,
        @Param("currentTime") currentTime: LocalTime,
        @Param("buildingName") buildingName: String
    ): List<Reservation>

    @Query("SELECT r FROM Reservation r WHERE r.classroom.floor.building.name = :buildingName")
    fun findAllByBuildingName(@Param("buildingName") buildingName: String): List<Reservation>

    @Query(
        "SELECT r FROM Reservation r " +
            "WHERE r.classroom.floor.building.name = :buildingName  " +
                "AND r.recurringId = :recurringId"
    )
    fun findAllByRecurringIdAndBuildingName(recurringId: UUID, buildingName: String): List<Reservation>

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.classroom.id = :classroomId
      AND r.date = :date  
      AND (:startTime < r.endTime AND :endTime > r.startTime)
      AND r.classroom.floor.building.id = :buildingId
""")
    fun findCollisions(
        @Param("classroomId") classroomId: UUID,
        @Param("date") date: LocalDate,
        @Param("startTime") startTime: LocalTime,
        @Param("endTime") endTime: LocalTime,
        @Param("buildingId") buildingId: String
    ): List<Reservation>

}