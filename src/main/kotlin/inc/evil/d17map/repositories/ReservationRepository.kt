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


    @Query("SELECT r FROM Reservation r WHERE r.date = :date ORDER BY r.date, r.startTime")
    fun findAllByDate(@Param("date") date: LocalDate): List<Reservation>


    @Query("SELECT r FROM Reservation r WHERE r.date BETWEEN :startDate AND :endDate ORDER BY r.date, r.startTime")
    fun findAllByDateBetween(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Reservation>

    @Query(
        "SELECT r " +
                "FROM Reservation r " +
                "WHERE r.user.id = :userID " +
                "AND r.date BETWEEN :startDate AND :endDate " +
                "ORDER BY r.date, r.startTime"
    )
    fun findAllByUserAndDateBetween(
        @Param("userID") userID: UUID,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Reservation>

    @Query(
        "SELECT r " +
                "FROM Reservation r " +
                "WHERE r.user.id = :userID " +
                "AND (r.date > :currentDate " +
                "OR (r.date = :currentDate AND r.startTime > :currentTime))" +
                "ORDER BY r.date, r.startTime"
    )
    fun findAllFuture(
        @Param("userID") userID: UUID,
        @Param("currentDate") currentDate: LocalDate,
        @Param("currentTime") currentTime: LocalTime,
    ): List<Reservation>

    @Query(
        "SELECT r " +
                "FROM Reservation r " +
                "WHERE (r.date > :currentDate " +
                "OR (r.date = :currentDate AND r.endTime >= :currentTime)) " +
                "AND r.type = 'EVENT' " +
                "ORDER BY r.date, r.startTime"
    )
    fun findAllCurrentOrFutureEvents(
        @Param("currentDate") currentDate: LocalDate,
        @Param("currentTime") currentTime: LocalTime
    ): List<Reservation>

}