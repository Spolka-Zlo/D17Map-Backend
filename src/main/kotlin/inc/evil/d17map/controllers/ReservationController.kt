package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ReservationRequest
import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.dtos.ReservationUpdateRequest
import inc.evil.d17map.services.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
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
    fun getReservationsByDay(
        @RequestParam(
            value = "day",
            required = true
        ) day: LocalDate
    ): ResponseEntity<List<ReservationResponse>> {
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
    fun createReservation(@RequestBody @Valid reservationRequest: ReservationRequest): ResponseEntity<ReservationResponse> {
        val createdReservation = reservationService.createReservation(reservationRequest)
        return ResponseEntity(createdReservation, HttpStatus.CREATED)
    }


    @Operation(
        summary = "Get all reservations for a week",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
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
        ) monday: LocalDate
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getReservationsForWeek(monday)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all reservations for a week for given user",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
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
        ) monday: LocalDate
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getUserWeekReservations(monday)
        return ResponseEntity(reservations, HttpStatus.OK)
    }


    @Operation(
        summary = "Get reservation by id",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservation."),
            ApiResponse(responseCode = "404", description = "Reservation with given id not found."),
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
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservation types."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/types")
    fun getReservationTypes(): ResponseEntity<List<String>> {
        return ResponseEntity(reservationService.getReservationTypes(), HttpStatus.OK)
    }

    @Operation(
        summary = "Get current user's future reservations",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved user's future reservations."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/user/future")
    fun getFutureReservations(): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getUserFutureReservations()
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Remove reservation by id",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully removed reservation."),
            ApiResponse(responseCode = "404", description = "Reservation with given id not found"),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeReservation(
        @PathVariable(
            required = true,
            name = "id"
        ) id: UUID
    ) = reservationService.removeReservation(id)


    @Operation(
        summary = "Update an existing reservation",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated reservation."),
            ApiResponse(responseCode = "400", description = "Invalid reservation data."),
            ApiResponse(responseCode = "404", description = "Reservation not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PutMapping("/{id}")
    fun updateReservation(
        @PathVariable id: UUID,
        @RequestBody @Valid updateRequest: ReservationUpdateRequest
    ): ResponseEntity<ReservationResponse> {
        val updatedReservation = reservationService.updateReservation(id, updateRequest)
        return ResponseEntity(updatedReservation, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all future and current events",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved events."),
            ApiResponse(responseCode = "400", description = "Invalid data."),
            ApiResponse(responseCode = "404", description = "Events not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/events")
    fun getEvents(
    ): ResponseEntity<List<ReservationResponse>> {
        val currentDateTime = LocalDateTime.now()
        val reservations = reservationService.getCurrentOrFutureEvents(currentDateTime)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Update an existing reservation by admin",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated reservation."),
            ApiResponse(responseCode = "400", description = "Invalid reservation data."),
            ApiResponse(responseCode = "404", description = "Reservation not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden access. The user does not have the necessary permissions."
            )
        ]
    )
    @PutMapping("/admin/{id}")
    fun updateReservationAdmin(
        @PathVariable id: UUID,
        @RequestBody @Valid adminUpdateRequest: ReservationRequest
    ): ResponseEntity<ReservationResponse> {
        val updatedReservation = reservationService.updateReservationAdmin(id, adminUpdateRequest)
        return ResponseEntity(updatedReservation, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all user's reservations (admin only)",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations."),
            ApiResponse(responseCode = "400", description = "Invalid user data."),
            ApiResponse(responseCode = "404", description = "Reservations not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden access. The user does not have the necessary permissions."
            )
        ]
    )
    @GetMapping("/admin/allReservations/{userId}")
    fun getUserReservationsForAdmin(
        @PathVariable userId: UUID
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getReservationsForUser(userId)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @GetMapping("/admin/allReservations")
    fun getAllReservationsForAdmin(
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getAllReservations()
        return ResponseEntity(reservations, HttpStatus.OK)
    }
}
