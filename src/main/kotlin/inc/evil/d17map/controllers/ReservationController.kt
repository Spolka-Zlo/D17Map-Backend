package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ReservationDto
import inc.evil.d17map.services.ReservationService
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/reservations")
class ReservationController(private val reservationService: ReservationService) {
    @GetMapping
    fun getReservationsByDay(@RequestParam("day") day: LocalDate?): ResponseEntity<Any> {
        if (day == null) {
            return ResponseEntity("Query parameter 'day' must be specified", HttpStatus.BAD_REQUEST)
        }

        val reservations = reservationService.getGivenDayReservations(day)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getReservationById(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        return try {
            val reservation = reservationService.getReservationById(id)
            ResponseEntity(reservation, HttpStatus.OK)
        } catch (e: EntityNotFoundException) {
            ResponseEntity("Reservation with id '$id' not found", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createReservation(@RequestBody reservationRequest: ReservationDto): ResponseEntity<ReservationDto> {
        val createdReservation = reservationService.createReservation(reservationRequest)
        return ResponseEntity(createdReservation, HttpStatus.CREATED)
    }

    @GetMapping("/{id}/future-reservations")
    fun getUserFutureReservations(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        return try {
            val futureReservations = reservationService.getUserFutureReservations(id)
            ResponseEntity(futureReservations, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("User not found or invalid ID", HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/week")
    fun getReservationsForWeek(@RequestParam("monday") monday: LocalDate?): ResponseEntity<Any> {
        if (monday == null) {
            return ResponseEntity("Query parameter 'monday' must be specified", HttpStatus.BAD_REQUEST)
        }
        val reservations = reservationService.getReservationsForWeek(monday)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @GetMapping("/my-week-reservations")
    fun getMyWeekReservations(): ResponseEntity<List<ReservationDto>> {
        val reservations = reservationService.getUserWeekReservations()
        return ResponseEntity(reservations, HttpStatus.OK)
    }
}
