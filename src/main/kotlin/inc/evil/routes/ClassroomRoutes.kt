package inc.evil.routes

import inc.evil.dto.ClassroomPostDto
import inc.evil.service.ClassroomService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.classroomRoutes(classroomService: ClassroomService) {
    route("/classrooms") {
        get {
            val classrooms = classroomService.getAll()
            // TODO check EmptyList<> serialization (reason may be located in lower layers
            // TODO add validation
            if (classrooms.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(classrooms)
            }
        }


        post {
            val classroomRequest = call.receive<ClassroomPostDto>()
            if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
                call.respond(HttpStatusCode.BadRequest, "Invalid classroom data")
                return@post
            }
            val classroomResponse = classroomService.createClassroom(classroomRequest)
            call.respond(HttpStatusCode.Created,classroomResponse)
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            classroomService.deleteById(UUID.fromString(id.toString()))
            call.respond(HttpStatusCode.NoContent)
        }


        get("{id}") {
            val id = call.parameters["id"]?.let { UUID.fromString(it) } ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            val classroom = classroomService.findById(id)
            if (classroom == null) {
                call.respond(HttpStatusCode.NotFound, "Classroom not found")
            } else {
                call.respond(classroom)
            }
        }

        put("{id}") {
            val id = call.parameters["id"]?.let { UUID.fromString(it) } ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            val classroomRequest = call.receive<ClassroomPostDto>()
            if (classroomRequest.name.isBlank() || classroomRequest.capacity <= 0) {
                call.respond(HttpStatusCode.BadRequest, "Invalid classroom data")
                return@put
            }
            val updatedClassroom = classroomService.updateClassroom(id, classroomRequest)
            if (updatedClassroom == null) {
                call.respond(HttpStatusCode.NotFound, "Classroom not found")
            } else {
                call.respond(updatedClassroom)
            }
        }

    }
}