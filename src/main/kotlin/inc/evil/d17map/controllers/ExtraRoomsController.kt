package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ExtraRoomRequest
import inc.evil.d17map.dtos.ExtraRoomResponse
import inc.evil.d17map.services.ExtraRoomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/extra-rooms/{buildingName}/{floorName}")
@Tag(name = "Extra Rooms")
class ExtraRoomController(private val extraRoomService: ExtraRoomService) {

    @Operation(
        summary = "Get all extra rooms on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all extra rooms."),
            ApiResponse(responseCode = "401", description = "Unauthorized access.")
        ]
    )
    @GetMapping
    fun getAllExtraRooms(
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createExtraRoom(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @RequestBody @Valid extraRoomRequest: ExtraRoomRequest
    ): ResponseEntity<ExtraRoomResponse> {
        val createdExtraRoom = extraRoomService.createExtraRoom(buildingName, floorName, extraRoomRequest)
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeExtraRoom(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @PathVariable id: UUID
    ) {
        extraRoomService.deleteExtraRoom(buildingName, floorName, id)
    }
}
