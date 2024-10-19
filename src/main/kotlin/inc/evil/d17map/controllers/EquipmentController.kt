package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.EquipmentDto
import inc.evil.d17map.services.EquipmentService
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/equipments")
@Tag(name = "Equipment")
class EquipmentController(private val equipmentService: EquipmentService) {

    @Operation(
        summary = "Get all equipments",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all equipments."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            ),
        ]
    )
    @GetMapping
    fun getAllEquipments(): ResponseEntity<List<EquipmentDto>> {
        val equipments = equipmentService.getAll()
        return ResponseEntity(equipments, HttpStatus.OK)
    }

    @Operation(
        summary = "Create a new equipment",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new equipment."),
            ApiResponse(responseCode = "400", description = "Invalid equipment request. The name field is required."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PostMapping
    fun createEquipment(@RequestBody equipmentRequest: EquipmentDto): ResponseEntity<Any> {
        if (equipmentRequest.name.isBlank()) {
            return ResponseEntity("Invalid equipment request", HttpStatus.BAD_REQUEST)
        }
        val createdEquipment = equipmentService.createEquipment(equipmentRequest)
        return ResponseEntity(createdEquipment, HttpStatus.CREATED)
    }

    @Hidden
    @PostMapping("/batch")
    fun createEquipmentsBatch(@RequestBody equipmentRequest: List<String>): ResponseEntity<Any> {
        if (equipmentRequest.isEmpty()) {
            return ResponseEntity("Empty equipment list", HttpStatus.BAD_REQUEST)
        }
        val createdEquipmentIds = equipmentService.createEquipmentBatch(equipmentRequest)
        return ResponseEntity(createdEquipmentIds, HttpStatus.CREATED)
    }
}
