package inc.evil.d17map.controllers

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
import java.util.*

@RestController
@Tag(name = "Classrooms")
class ClassroomController(private val classroomService: ClassroomService) {

    @Operation(
        summary = "Get all classrooms in a specific building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all classrooms for the building."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in.")
        ]
    )
    @GetMapping("/building/{buildingName}/classrooms")
    fun getAllClassroomsByBuilding(
        @PathVariable buildingName: String
    ): ResponseEntity<List<ClassroomResponse>> {
        val classrooms = classroomService.getAllClassroomsByBuilding(buildingName)
        return ResponseEntity(classrooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Get all classrooms on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved all classrooms."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in.")
        ]
    )
    @GetMapping("/building/{buildingName}/floor/{floorName}/classrooms")
    fun getAllClassroomsByBuildingAndFloor(
        @PathVariable buildingName: String,
        @PathVariable floorName: String
    ): ResponseEntity<List<ClassroomResponse>> {
        val classrooms = classroomService.getByBuildingAndFloor(buildingName, floorName)
        return ResponseEntity(classrooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Create a new classroom on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "201", description = "Successfully created new classroom."),
            ApiResponse(responseCode = "400", description = "Invalid classroom data."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in.")
        ]
    )
    @PostMapping("/building/{buildingName}/floor/{floorName}/classrooms")
    @ResponseStatus(HttpStatus.CREATED)
    fun createClassroom(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @RequestBody @Valid classroomRequest: ClassroomRequest
    ): ResponseEntity<ClassroomResponse> {
        val createdClassroom = classroomService.createClassroom(buildingName, floorName, classroomRequest)
        return ResponseEntity(createdClassroom, HttpStatus.CREATED)
    }

    @Operation(
        summary = "Get available classrooms by date, time range, and people count for a specific building and floor",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved available classrooms."),
            ApiResponse(responseCode = "400", description = "Invalid criteria data provided.")
        ]
    )
    @GetMapping("/building/{buildingName}/floor/{floorName}/classrooms/available")
    fun getAvailableClassrooms(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @RequestParam date: LocalDate,
        @RequestParam timeRange: String,
        @RequestParam peopleCount: Int
    ): ResponseEntity<List<ClassroomResponse>> {
        val classrooms = classroomService.findAvailableClassrooms(buildingName, floorName, date, timeRange, peopleCount)
        return ResponseEntity(classrooms, HttpStatus.OK)
    }

    @Operation(
        summary = "Update a classroom by admin on a specific building and floor",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated the classroom."),
            ApiResponse(responseCode = "404", description = "Classroom with the given ID not found."),
            ApiResponse(responseCode = "400", description = "Invalid classroom data."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in.")
        ]
    )
    @PutMapping("/building/{buildingName}/floor/{floorName}/classrooms/admin/{id}")
    fun updateClassroomAdmin(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @PathVariable id: UUID,
        @RequestBody @Valid classroomRequest: ClassroomRequest
    ): ResponseEntity<ClassroomResponse> {
        val updatedClassroom = classroomService.updateClassroom(buildingName, floorName, id, classroomRequest)
        return ResponseEntity(updatedClassroom, HttpStatus.OK)
    }

    @Operation(
        summary = "Get photo of a classroom by ID on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully retrieved photo."),
            ApiResponse(responseCode = "404", description = "Classroom with the given ID not found."),
            ApiResponse(responseCode = "400", description = "Invalid classroom data provided.")
        ]
    )
    @GetMapping("/building/{buildingName}/floor/{floorName}/classrooms/{id}/photo")
    fun getClassroomPhoto(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @PathVariable id: UUID
    ): ResponseEntity<ByteArray> {
        val photo = classroomService.getClassroomPhotoById(buildingName, floorName, id)
        return ResponseEntity.ok()
            .header("Content-Type", "image/jpeg")
            .body(photo)
    }

    @Operation(
        summary = "Delete a classroom by ID on a specific floor of a building",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully deleted the classroom."),
            ApiResponse(responseCode = "404", description = "Classroom with the given ID not found."),
            ApiResponse(responseCode = "401", description = "Unauthorized access. The user is not authenticated and needs to log in.")
        ]
    )
    @DeleteMapping("/building/{buildingName}/floor/{floorName}/classrooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun removeClassroom(
        @PathVariable buildingName: String,
        @PathVariable floorName: String,
        @PathVariable id: UUID
    ) {
        classroomService.deleteByBuildingAndFloor(buildingName, floorName, id)
    }
}
