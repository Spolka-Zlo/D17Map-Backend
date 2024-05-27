package inc.evil.routes

import inc.evil.dto.ClassroomFullDto
import inc.evil.service.ClassroomService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.classroomRoutes(classroomService: ClassroomService) {
    route("/classroom") {
        // list all classrooms
        get {
            val classrooms = classroomService.getAll()
            // TODO add pagination / query parameters
            // TODO add validation
            call.respond(classrooms)
        }

        // get details of selected classroom if you know its id
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val uuid = UUID.fromString(id)
            val classroom = classroomService.getByID(uuid) ?: return@get call.respondText(
                "Classroom not found",
                status = HttpStatusCode.NotFound
            )


            call.respond(classroom)
        }

        // get details of selected classroom if you know its name
        get("/{name}") {
            val name = call.parameters["name"] ?: return@get call.respondText(
                "Missing or malformed name",
                status = HttpStatusCode.BadRequest
            )

            val classroom = classroomService.getByName(name) ?: return@get call.respondText(
                "Classroom not found",
                status = HttpStatusCode.NotFound
            )

           call.respond(classroom)
        }

        // create new classroom
        post {
            val classroom = call.receive<ClassroomFullDto>()

            val createdClassroom = classroomService.post(classroom) ?: return@post call.respondText(
                "Error while creating classroom",
                status = HttpStatusCode.InternalServerError
            )

            call.respond(createdClassroom)
        }

        // send only fields you want to change in json
        patch("/{id}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val classroom = call.receive<ClassroomFullDto>()
            val updatedClassroom = classroomService.patch(classroom) ?: return@patch call.respondText(
                "Error while updating classroom",
                status = HttpStatusCode.InternalServerError
            )

            call.respond(updatedClassroom)
        }


        // to delete you have to know the id for security reasons
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val uuid = UUID.fromString(id)

            classroomService.delete(uuid)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}