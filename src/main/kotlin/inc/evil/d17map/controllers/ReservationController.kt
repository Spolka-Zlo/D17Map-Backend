package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ReservationDto
import inc.evil.d17map.services.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/reservations")
@Tag(name = "Reservations")
class ReservationController(private val reservationService: ReservationService) {
    @Operation(
        summary = "Get reservations by day",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved reservations for the specified day."
            ),
            ApiResponse(responseCode = "400", description = "Query parameter 'day' must be specified."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping
    fun getReservationsByDay(@RequestParam("day") day: LocalDate?): ResponseEntity<Any> {
        if (day == null) {
            return ResponseEntity("Query parameter 'day' must be specified", HttpStatus.BAD_REQUEST)
        }

        val reservations = reservationService.getGivenDayReservations(day)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Get reservation by ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservation."),
            ApiResponse(responseCode = "404", description = "Reservation not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/{id}")
    fun getReservationById(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        return try {
            val reservation = reservationService.getReservationById(id)
            ResponseEntity(reservation, HttpStatus.OK)
        } catch (e: EntityNotFoundException) {
            ResponseEntity("Reservation with id '$id' not found", HttpStatus.NOT_FOUND)
        }
    }

    @Operation(
        summary = "Create a new reservation",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new reservation."),
            ApiResponse(responseCode = "400", description = "Invalid reservation data."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PostMapping
    fun createReservation(@RequestBody reservationRequest: ReservationDto): ResponseEntity<ReservationDto> {
        val createdReservation = reservationService.createReservation(reservationRequest)
        return ResponseEntity(createdReservation, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Get future reservations for a user",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved future reservations."),
            ApiResponse(responseCode = "404", description = "User not found or invalid ID."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/{id}/future-reservations")
    fun getUserFutureReservations(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        return try {
            val futureReservations = reservationService.getUserFutureReservations(id)
            ResponseEntity(futureReservations, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("User not found or invalid ID", HttpStatus.NOT_FOUND)
        }
    }

    @Operation(
        summary = "Get all reservations for a week",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
            ApiResponse(responseCode = "404", description = "Reservations for given week not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/week")
    fun getReservationsForWeek(@RequestParam("monday") monday: LocalDate?): ResponseEntity<Any> {
        if (monday == null) {
            return ResponseEntity("Query parameter 'monday' must be specified", HttpStatus.BAD_REQUEST)
        }
        val reservations = reservationService.getReservationsForWeek(monday)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all reservations for a week for given user",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
            ApiResponse(responseCode = "404", description = "User reservations for given week not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/my-week-reservations")
    fun getMyWeekReservations(): ResponseEntity<List<ReservationDto>> {
        val reservations = reservationService.getUserWeekReservations()
        return ResponseEntity(reservations, HttpStatus.OK)
    }
}
