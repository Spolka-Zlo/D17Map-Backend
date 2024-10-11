package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.ClassroomDto
import inc.evil.d17map.services.ClassroomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/classrooms")
class ClassroomController(private val classroomService: ClassroomService) {

    @GetMapping
    fun getAllClassrooms(): ResponseEntity<List<ClassroomDto>> {
        val classrooms = classroomService.getAll()
        return if (classrooms.isEmpty()) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(classrooms, HttpStatus.OK)
        }
    }

    @GetMapping("/{id}")
    fun getClassroomById(@PathVariable id: UUID): ResponseEntity<ClassroomDto> {
        val classroom = classroomService.findById(id)
        return ResponseEntity(classroom, HttpStatus.OK)
    }

    @PostMapping
    fun createClassroom(@RequestBody classroomRequest: ClassroomDto): ResponseEntity<Any> {
        if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
            return ResponseEntity("Invalid classroom data", HttpStatus.BAD_REQUEST)
        }
        val createdClassroom = classroomService.createClassroom(classroomRequest)
        return ResponseEntity(createdClassroom, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateClassroom(@PathVariable id: UUID, @RequestBody classroomRequest: ClassroomDto): ResponseEntity<Any> {
        if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
            return ResponseEntity("Invalid classroom data", HttpStatus.BAD_REQUEST)
        }
        val updatedClassroom = classroomService.updateClassroom(id, classroomRequest)
        return ResponseEntity(updatedClassroom, HttpStatus.OK)
    }

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
