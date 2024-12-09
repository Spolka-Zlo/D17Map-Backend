package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.exceptions.InvalidClassroomDataException
import inc.evil.d17map.services.ClassroomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/classrooms")
@Tag(name = "Classrooms")
class ClassroomController(private val classroomService: ClassroomService) {

    @Operation(
        summary = "Get all classrooms",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all classrooms."),
        ]
    )
    @GetMapping
    fun getAllClassrooms(): ResponseEntity<List<ClassroomResponse>> {
        val classrooms = classroomService.getAll()
        return ResponseEntity(classrooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Create a new classroom",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new classroom."),
            ApiResponse(responseCode = "400", description = "Invalid classroom data."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    fun createClassroom(@RequestBody @Valid classroomRequest: ClassroomRequest): ResponseEntity<ClassroomResponse> {
        if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
            throw InvalidClassroomDataException()
        }
        val createdClassroom = classroomService.createClassroom(classroomRequest)
        return ResponseEntity(createdClassroom, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Get available classrooms by date, time range, and people count",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved available classrooms."),
            ApiResponse(responseCode = "400", description = "Invalid criteria data provided.")
        ]
    )
    @GetMapping("/available")
    fun getAvailableClassrooms(
        @RequestParam date: LocalDate,
        @RequestParam timeRange: String,
        @RequestParam peopleCount: Int
    ): ResponseEntity<List<ClassroomResponse>> {
        val classrooms = classroomService.findAvailableClassrooms(date, timeRange, peopleCount)
        return ResponseEntity(classrooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Update a classroom by admin",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated the classroom."),
            ApiResponse(responseCode = "404", description = "Classroom with the given ID not found."),
            ApiResponse(responseCode = "400", description = "Invalid classroom data."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateClassroomAdmin(
        @PathVariable id: UUID,
        @RequestBody @Valid classroomRequest: ClassroomRequest
    ): ResponseEntity<ClassroomResponse> {
        val updatedClassroom = classroomService.updateClassroom(id, classroomRequest)
        return ResponseEntity(updatedClassroom, HttpStatus.OK)
    }

    @Operation(
        summary = "Delete a classroom by ID",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully deleted the classroom."),
            ApiResponse(responseCode = "404", description = "Classroom with the given ID not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    fun removeClassroom(@PathVariable id: UUID) {
        classroomService.deleteById(id)
    }
}
