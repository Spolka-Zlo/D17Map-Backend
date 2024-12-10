package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.FloorRequest
import inc.evil.d17map.dtos.FloorResponse
import inc.evil.d17map.services.FloorService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/floors")
@Tag(name = "Floors")
class FloorController(private val floorService: FloorService) {
    @Operation(
        summary = "Get all floors",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all floors."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping
    fun getAllFloors(): ResponseEntity<List<FloorResponse>> {
        val floors = floorService.getFloors()
        val floorResponses = floors.map {
            FloorResponse(
                id = it.id!!,
                name = it.name,
                buildingName = it.building.name
            )
        }
        return ResponseEntity(floorResponses, HttpStatus.OK)
    }

    @Operation(
        summary = "Create a new floor",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new floor."),
            ApiResponse(responseCode = "400", description = "Invalid floor data."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PostMapping
    fun createFloor(@RequestBody @Valid floorRequest: FloorRequest): ResponseEntity<FloorResponse> {
        val createdFloor = floorService.createFloor(floorRequest)
        return ResponseEntity(createdFloor, HttpStatus.CREATED)
    }
}