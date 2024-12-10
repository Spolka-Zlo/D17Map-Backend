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


@RestController
@RequestMapping("/extra-rooms")
@Tag(name = "Extra Rooms")
class ExtraRoomsController(private val extraRoomService: ExtraRoomService) {

    @Operation(
        summary = "Get all extra rooms",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all extra rooms."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            ),
        ]
    )
    @GetMapping
    fun getAllExtraRooms(): ResponseEntity<List<ExtraRoomResponse>> {
        val extraRooms = extraRoomService.getExtraRooms()
        return ResponseEntity(extraRooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Create a new extra room",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new extra room."),
            ApiResponse(responseCode = "400", description = "Invalid extra room request. The name field is required."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createExtraRoom(@RequestBody @Valid extraRoomRequest: ExtraRoomRequest): ResponseEntity<ExtraRoomResponse> {
        val createdExtraRoom = extraRoomService.createExtraRoom(extraRoomRequest)
        return ResponseEntity(createdExtraRoom, HttpStatus.CREATED)
    }
}