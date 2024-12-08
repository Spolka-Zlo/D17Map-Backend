package inc.evil.d17map.controllers

import inc.evil.d17map.exceptions.InvalidEquipmentDataException
import inc.evil.d17map.dtos.EquipmentRequest
import inc.evil.d17map.dtos.EquipmentResponse
import inc.evil.d17map.services.EquipmentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.security.PermitAll
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/equipments")
@Tag(name = "Equipment")
class EquipmentController(private val equipmentService: EquipmentService) {

    @Operation(
        summary = "Get all equipments",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all equipments.")
        ]
    )
    @GetMapping
    @PermitAll
    fun getAllEquipments(): ResponseEntity<List<EquipmentResponse>> {
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    fun createEquipment(@RequestBody @Valid equipmentRequest: EquipmentRequest): ResponseEntity<EquipmentResponse> {
        if (equipmentRequest.name.isBlank()) {
            throw InvalidEquipmentDataException.blankName()
        }
        val createdEquipment = equipmentService.createEquipment(equipmentRequest)
        return ResponseEntity(createdEquipment, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Update an existing equipment",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated equipment."),
            ApiResponse(responseCode = "400", description = "Invalid equipment request."),
            ApiResponse(responseCode = "404", description = "Equipment with the given ID not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateEquipmentAdmin(
        @PathVariable id: UUID,
        @RequestBody @Valid equipmentRequest: EquipmentRequest
    ): ResponseEntity<EquipmentResponse> {
        val updatedEquipment = equipmentService.updateEquipment(id, equipmentRequest)
        return ResponseEntity(updatedEquipment, HttpStatus.OK)
    }

    @Operation(
        summary = "Delete an equipment by ID",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully deleted the equipment."),
            ApiResponse(responseCode = "404", description = "Equipment with the given ID not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun removeEquipment(@PathVariable id: UUID) {
        equipmentService.removeEquipment(id)
    }
}
