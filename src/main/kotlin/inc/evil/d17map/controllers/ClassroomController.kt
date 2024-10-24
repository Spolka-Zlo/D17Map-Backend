package inc.evil.d17map.controllers

import inc.evil.d17map.InvalidClassroomDataException
import inc.evil.d17map.dtos.ClassroomRequest
import inc.evil.d17map.dtos.ClassroomResponse
import inc.evil.d17map.services.ClassroomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun createClassroom(@RequestBody classroomRequest: ClassroomRequest): ResponseEntity<ClassroomResponse> {
        if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
            throw InvalidClassroomDataException()
        }
        val createdClassroom = classroomService.createClassroom(classroomRequest)
        return ResponseEntity(createdClassroom, HttpStatus.CREATED)
    }

}
