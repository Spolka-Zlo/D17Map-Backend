package inc.evil.routes

import inc.evil.dto.ClassroomDetailsDto
import inc.evil.dto.ClassroomFullDto
import inc.evil.service.ClassroomService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


val classroom1Details = ClassroomDetailsDto(2, listOf("Laptop", "Gniazdko"))
val classroom2Details = ClassroomDetailsDto(3, listOf("Komputer", "Rzutnik", "Kamera"))
val classroom1 = ClassroomFullDto(UUID.randomUUID(), "Sala sieciowa", classroom1Details)
val classroom2 = ClassroomFullDto(UUID.randomUUID(), "Sala wykladowa", classroom2Details)

val classrooms: List<ClassroomFullDto> = listOf(classroom1, classroom2)

fun Route.classroomRoutes(classroomService: ClassroomService) {
    route("/classroom") {
        // list all classrooms
        get {
            // TODO create service that gets data from database
            // TODO add pagination / query parameters
            // TODO add validation
            call.respond(classrooms)
        }

        // get details of selected classroom if you know its id
        get("/{id}") {
            call.respondText(call.parameters["id"].toString()) {}
        }

        // get details of selected classroom if you know its name
        get("/{name}") {
            call.respondText(call.parameters["name"].toString()) {}
        }

        // create new classroom
        post {
            call.respondText(call.parameters["name"].toString()) {}
        }

        // send only fields you want to change in json
        patch("/{id}") {
            call.respondText(call.parameters["id"].toString()) {}
        }


        // to delete you have to know the id for security reasons
        delete("/{id}") {
            call.respondText(call.parameters["id"].toString()) {}
        }
    }
}