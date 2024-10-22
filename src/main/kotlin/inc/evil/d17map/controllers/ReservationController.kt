package inc.evil.d17map.controllers

import inc.evil.d17map.MissingParameterException
import inc.evil.d17map.dtos.ReservationRequest
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.enums.ReservationType
import inc.evil.d17map.services.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

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
    fun getReservationsByDay(
        @RequestParam(
            value = "day",
            required = true
        ) @DateTimeFormat(pattern = "dd-MM-yyyy") day: LocalDate?
    ): ResponseEntity<List<ReservationResponse>> {
        if (day == null) {
            throw MissingParameterException("day")
        }

        val reservations = reservationService.getGivenDayReservations(day)
        return ResponseEntity(reservations, HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.CREATED)
    fun createReservation(@RequestBody reservationRequest: ReservationRequest): ResponseEntity<ReservationResponse> {
        val createdReservation = reservationService.createReservation(reservationRequest)
        return ResponseEntity(createdReservation, HttpStatus.CREATED)
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
    fun getReservationsForWeek(
        @RequestParam(
            name = "startDay",
            required = true
        ) @DateTimeFormat(pattern = "dd-MM-yyyy") monday: LocalDate?
    ): ResponseEntity<List<ReservationResponse>> {
        if (monday == null) {
            throw MissingParameterException("startDay")
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
    @GetMapping("/user/week")
    fun getMyWeekReservations(
        @RequestParam(
            name = "startDay",
            required = true
        ) @DateTimeFormat(pattern = "dd-MM-yyyy") monday: LocalDate
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getUserWeekReservations(monday)
        return ResponseEntity(reservations, HttpStatus.OK)
    }


    @Operation(
        summary = "Get reservation by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
            ApiResponse(responseCode = "404", description = "User reservations for given week not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/{id}")
    fun getReservationById(
        @PathVariable(
            required = true,
            name = "id"
        ) id: UUID
    ): ResponseEntity<ReservationResponse> {
        val reservations = reservationService.getReservationById(id)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Get reservation types",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
            ApiResponse(responseCode = "404", description = "User reservations for given week not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/types")
    fun getReservationTypes(): ResponseEntity<List<ReservationType>> {
        return ResponseEntity(reservationService.getReservationTypes(), HttpStatus.OK)
    }

}
