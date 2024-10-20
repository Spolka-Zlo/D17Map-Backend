package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ClassroomDto
import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.services.ClassroomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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
    fun getAllClassrooms(): ResponseEntity<List<ClassroomDto>> {
        val classrooms = classroomService.getAll()
        return ResponseEntity(classrooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Get a classroom by ID",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved classroom."),
            ApiResponse(responseCode = "404", description = "Classroom not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping("/{id}")
    fun getClassroomById(@PathVariable id: UUID): ResponseEntity<ClassroomDto> {
        val classroom = classroomService.findById(id)
        return ResponseEntity(classroom, HttpStatus.OK)
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
    fun createClassroom(@RequestBody classroomRequest: ClassroomRequest): ResponseEntity<Any> {
        if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
            return ResponseEntity("Invalid classroom data", HttpStatus.BAD_REQUEST)
        }
        val createdClassroom = classroomService.createClassroom(classroomRequest)
        return ResponseEntity(createdClassroom, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Update a classroom",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated classroom."),
            ApiResponse(responseCode = "404", description = "Classroom not found."),
            ApiResponse(responseCode = "400", description = "Invalid classroom data."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PutMapping("/{id}")
    fun updateClassroom(@PathVariable id: UUID, @RequestBody classroomRequest: ClassroomDto): ResponseEntity<Any> {
        if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
            return ResponseEntity("Invalid classroom data", HttpStatus.BAD_REQUEST)
        }
        val updatedClassroom = classroomService.updateClassroom(id, classroomRequest)
        return ResponseEntity(updatedClassroom, HttpStatus.OK)
    }

    @Operation(
        summary = "Delete a classroom",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully deleted classroom."),
            ApiResponse(responseCode = "404", description = "Classroom not found."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteClassroom(@PathVariable id: UUID): ResponseEntity<Void> {
        return try {
            classroomService.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: IllegalArgumentException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
