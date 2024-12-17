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
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RestController
@Tag(name = "Reservations")
class ReservationController(private val reservationService: ReservationService) {

    companion object {
        private const val BUILDING_PATH = "/buildings/{buildingName}/reservations"
    }

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
    @GetMapping(BUILDING_PATH)
    fun getReservationsByDay(
        @RequestParam(value = "day", required = true) day: LocalDate,
        @PathVariable buildingName: String
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getGivenDayReservations(day, buildingName)
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
    @PostMapping(BUILDING_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    fun createReservation(
        @PathVariable buildingName: String,
        @RequestBody @Valid reservationRequest: ReservationRequest
    ): ResponseEntity<ReservationResponse> {
        val createdReservation = reservationService.createReservation(buildingName, reservationRequest)
        return ResponseEntity(createdReservation, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Get all reservations for a week in a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for next week."),
            ApiResponse(responseCode = "400", description = "Invalid building name or date."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("$BUILDING_PATH/week")
    fun getReservationsForWeek(
        @RequestParam(name = "startDay", required = true) monday: LocalDate,
        @PathVariable buildingName: String,
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getReservationsForWeek(monday, buildingName)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all reservations for a week for given user in a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations for the week."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("$BUILDING_PATH/user/week")
    fun getMyWeekReservations(
        @RequestParam(name = "startDay", required = true) monday: LocalDate,
        @PathVariable buildingName: String,
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getUserWeekReservations(monday, buildingName)
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
    @GetMapping("$BUILDING_PATH/{id}")
    fun getReservationById(
        @PathVariable(name = "id", required = true) id: UUID,
        @PathVariable buildingName: String,
    ): ResponseEntity<ReservationResponse> {
        val reservations = reservationService.getReservationById(buildingName, id)
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
    @GetMapping("reservations/types")
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
    @GetMapping("$BUILDING_PATH/user/future")
    fun getFutureReservations(
        @PathVariable buildingName: String,
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getUserFutureReservations(buildingName)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Remove reservation by id for a specific building",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully removed reservation."),
            ApiResponse(responseCode = "404", description = "Reservation with given id not found"),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @DeleteMapping("$BUILDING_PATH/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    fun removeReservation(
        @PathVariable id: UUID,
        @PathVariable buildingName: String
    ) = reservationService.removeReservation(id, buildingName)

    @Operation(
        summary = "Update an existing reservation in a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated reservation."),
            ApiResponse(responseCode = "400", description = "Invalid reservation data."),
            ApiResponse(responseCode = "404", description = "Reservation not found."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in.")
        ]
    )
    @PutMapping("$BUILDING_PATH/{id}")
    fun updateReservation(
        @PathVariable id: UUID,
        @PathVariable buildingName: String,
        @RequestBody @Valid updateRequest: ReservationUpdateRequest
    ): ResponseEntity<ReservationResponse> {
        val updatedReservation = reservationService.updateReservation(id, buildingName, updateRequest)
        return ResponseEntity(updatedReservation, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all future and current events for a specific building",
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
    @GetMapping("$BUILDING_PATH/events")
    fun getEvents(
        @PathVariable buildingName: String,
    ): ResponseEntity<List<ReservationResponse>> {
        val currentDateTime = LocalDateTime.now()
        val reservations = reservationService.getCurrentOrFutureEvents(currentDateTime, buildingName)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Update an existing reservation by admin for a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated reservation."),
            ApiResponse(responseCode = "400", description = "Invalid reservation data."),
            ApiResponse(responseCode = "404", description = "Reservation not found."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in."),
            ApiResponse(responseCode = "403", description = "Forbidden access. The user does not have the necessary permissions.")
        ]
    )
    @PutMapping("$BUILDING_PATH/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateReservationAdmin(
        @PathVariable id: UUID,
        @PathVariable buildingName: String,
        @RequestBody @Valid adminUpdateRequest: ReservationRequest
    ): ResponseEntity<ReservationResponse> {
        val updatedReservation = reservationService.updateReservationAdmin(id,  buildingName, adminUpdateRequest)
        return ResponseEntity(updatedReservation, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all user's reservations (admin only) for a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved reservations."),
            ApiResponse(responseCode = "400", description = "Invalid user or building data."),
            ApiResponse(responseCode = "404", description = "Reservations not found."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in."),
            ApiResponse(responseCode = "403", description = "Forbidden access. The user does not have the necessary permissions.")
        ]
    )
    @GetMapping("$BUILDING_PATH/admin/allReservations/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUserReservationsForAdmin(
        @PathVariable buildingName: String,
        @PathVariable userId: UUID,
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getReservationsForUserInBuilding(userId, buildingName)
        return ResponseEntity(reservations, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all reservations for a building (Admin only)",
        description = "Retrieve all reservations for a specific building. This endpoint is only accessible to admin users.",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all reservations for the specified building."),
            ApiResponse(responseCode = "400", description = "Invalid building name provided."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in."),
            ApiResponse(responseCode = "403", description = "Forbidden access. The user does not have the necessary permissions."),
            ApiResponse(responseCode = "404", description = "Building not found.")
        ]
    )
    @GetMapping("$BUILDING_PATH/admin/allReservations")
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllReservationsForAdmin(
        @PathVariable buildingName: String
    ): ResponseEntity<List<ReservationResponse>> {
        val reservations = reservationService.getAllReservationsForBuilding(buildingName)
        return ResponseEntity(reservations, HttpStatus.OK)
    }
}
