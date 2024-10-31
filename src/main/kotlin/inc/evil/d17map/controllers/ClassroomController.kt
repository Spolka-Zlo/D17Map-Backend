package inc.evil.d17map.controllers

import inc.evil.d17map.exceptions.InvalidClassroomDataException
import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.services.ClassroomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/classrooms")
@Tag(name = "Classrooms")
class ClassroomController(private val classroomService: ClassroomService) {

    @Operation(
        summary = "Get all classrooms",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all classrooms."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
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
}
