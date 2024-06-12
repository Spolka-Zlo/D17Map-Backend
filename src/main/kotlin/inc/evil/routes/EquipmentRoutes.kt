package inc.evil.routes


import inc.evil.dto.ClassroomPostDto
import inc.evil.dto.EquipmentPostDto
import inc.evil.service.EquipmentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.equipmentRoutes(equipmentService: EquipmentService) {
    route("/equipments") {
        /*
        * [{
        *    "id": 1234,
        *    "name": "3.14"
        * }, ...
        * ]
        *
        * */
        get {
            val equipments = equipmentService.getAll()
            // TODO check EmptyList<> serialization (reason may be located in lower layers
            // TODO add validation
            if (equipments.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(equipments)
            }
        }

        /*
       * {
       *    "name": "3.14"
       * }
       *
       *
       * */
        post {
            val equipmentRequest = call.receive<EquipmentPostDto>()
            if (equipmentRequest.name.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid equipment data")
                return@post
            }
            val equipmentResponse = equipmentService.createEquipment(equipmentRequest)
            call.respond(HttpStatusCode.Created, equipmentResponse)
        }

    }
}