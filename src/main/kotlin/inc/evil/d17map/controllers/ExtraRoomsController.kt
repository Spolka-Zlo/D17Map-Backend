package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ExtraRoomRequest
import inc.evil.d17map.dtos.ExtraRoomResponse
import inc.evil.d17map.dtos.ExtraRoomUpdateRequest
import inc.evil.d17map.services.ExtraRoomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Tag(name = "Extra Rooms")
class ExtraRoomController(private val extraRoomService: ExtraRoomService) {

    companion object {
        private const val BUILDINGS_PATH = "/buildings/{buildingName}"
        private const val FLOORS_PATH = "/floors/{floorName}"
        private const val EXTRA_ROOMS_PATH = "/extra-rooms"
    }

    @Operation(
        summary = "Get all extra rooms in a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all extra rooms for the building."),
            ApiResponse(responseCode = "401", description = "Unauthorized access.")
        ]
    )
    @GetMapping("$BUILDINGS_PATH$EXTRA_ROOMS_PATH")
    fun getAllExtraRoomsByBuilding(
        @PathVariable buildingName: String
    ): ResponseEntity<List<ExtraRoomResponse>> {
        val extraRooms = extraRoomService.getExtraRoomsByBuilding(buildingName)
        return ResponseEntity(extraRooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all extra rooms on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all extra rooms.")
        ]
    )
    @GetMapping("$BUILDINGS_PATH$FLOORS_PATH$EXTRA_ROOMS_PATH")
    fun getAllExtraRoomsByBuildingAndFloor(
        @PathVariable buildingName: String,
        @PathVariable floorName: String
    ): ResponseEntity<List<ExtraRoomResponse>> {
        val extraRooms = extraRoomService.getExtraRooms(buildingName, floorName)
        return ResponseEntity(extraRooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Create a new extra room on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new extra room."),
            ApiResponse(responseCode = "400", description = "Invalid extra room request."),
            ApiResponse(responseCode = "401", description = "Unauthorized access.")
        ]
    )
    @PostMapping("$BUILDINGS_PATH$EXTRA_ROOMS_PATH")
    @PreAuthorize("hasRole('ADMIN')")
    fun createExtraRoom(
        @PathVariable buildingName: String,
        @RequestBody @Valid extraRoomRequest: ExtraRoomRequest
    ): ResponseEntity<ExtraRoomResponse> {
        val createdExtraRoom =
            extraRoomService.createExtraRoom(buildingName, extraRoomRequest.floorName, extraRoomRequest)
        return ResponseEntity(createdExtraRoom, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Delete an extra room by ID on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully deleted the extra room."),
            ApiResponse(responseCode = "404", description = "Extra room with the given ID not found."),
            ApiResponse(responseCode = "401", description = "Unauthorized access.")
        ]
    )
    @DeleteMapping("$BUILDINGS_PATH$EXTRA_ROOMS_PATH/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeExtraRoom(
        @PathVariable buildingName: String,
        @PathVariable id: UUID
    ) {
        extraRoomService.deleteExtraRoom(buildingName, id)
    }

    @Operation(
        summary = "Update an existing extra room by ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated the extra room."),
            ApiResponse(responseCode = "400", description = "Invalid extra room data."),
            ApiResponse(responseCode = "404", description = "Extra room not found."),
            ApiResponse(responseCode = "401", description = "Unauthorized access.")
        ]
    )
    @PutMapping("$BUILDINGS_PATH$EXTRA_ROOMS_PATH/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateExtraRoom(
        @PathVariable buildingName: String,
        @PathVariable id: UUID,
        @RequestBody @Valid extraRoomRequest: ExtraRoomUpdateRequest
    ): ResponseEntity<ExtraRoomResponse> {
        val updatedExtraRoom = extraRoomService.updateExtraRoom(buildingName, id, extraRoomRequest)
        return ResponseEntity(updatedExtraRoom, HttpStatus.OK)
    }
}