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
    fun getReservationsByDay(@RequestParam("day") day: String?): ResponseEntity<Any> {
        if (day == null) {
            return ResponseEntity("Query parameter 'day' must be specified", HttpStatus.BAD_REQUEST)
        }

        val parsedDate = try {
            LocalDate.parse(day)
        } catch (ex: Exception) {
            return ResponseEntity("Invalid date format. Expected YYYY-MM-DD", HttpStatus.BAD_REQUEST)
        }

        val reservations = reservationService.getGivenDayReservations(parsedDate)
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
}
