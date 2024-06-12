package inc.evil.routes


import inc.evil.dto.EquipmentPostDto
import inc.evil.service.EquipmentService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.equipmentRoutes(equipmentService: EquipmentService) {
    route("/equipments") {
        get {
            val equipments = equipmentService.getAll()
            if (equipments.isEmpty()) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(equipments)
            }
        }

        post {
            val equipmentRequest = call.receive<EquipmentPostDto>()
            if (equipmentRequest.name.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Invalid equipment request")
            }
            val equipmentResponse = equipmentService.createEquipment(equipmentRequest)
            call.respond(HttpStatusCode.Created, equipmentResponse)
        }

        // not sure about this one, ktor says it cannot handle generic Lists, but it actually does
        post("/batch") {
            val equipmentRequest = call.receive<List<String>>()
            if (equipmentRequest.isEmpty()){
                call.respond(HttpStatusCode.BadRequest, "Empty equipment list")
            }
            val createdEquipmentIds = equipmentService.createEquipment(equipmentRequest)
            call.respond(HttpStatusCode.Created, createdEquipmentIds)
        }
    }
}