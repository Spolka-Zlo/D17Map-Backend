package inc.evil.routes

import inc.evil.dto.ClassroomPostDto
import inc.evil.service.ClassroomService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.classroomRoutes(classroomService: ClassroomService) {
    route("/classrooms") {
        /*
        * [{
        *    "id": 1234,
        *    "name": "3.14"
        *    "capacity": 34
        *    "equipment":
        *    [
        *      "projektor",
        *      "laptop",
        *      "tablica"
        *    ]
        * }, ...
        * ]
        *
        * */
        get {
            val classrooms = classroomService.getAll()
            // TODO add pagination / query parameters
            // TODO add validation
            call.respond(classrooms)
        }

        /*
       * {
       *    "name": "3.14",
       *    "description": "Sala Marka Maruchy",
       *    "capacity": 34,
       *    "equipment":
       *    [
       *      "projektor",
       *      "laptop",
       *      "tablica"
       *    ]
       * }
       *
       *
       * */
        post {
            val classroomRequest = call.receive<ClassroomPostDto>()
            val classroomResponse = classroomService.createClassroom(classroomRequest)
            // TODO add validation
            call.respond(HttpStatusCode.Created,classroomResponse)
        }

    }
}