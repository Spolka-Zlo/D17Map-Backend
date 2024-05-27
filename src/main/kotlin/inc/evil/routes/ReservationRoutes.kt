package inc.evil.routes

import inc.evil.dto.ReservationFullDto
import inc.evil.service.ReservationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.reservationRoutes(reservationService: ReservationService) {
    route("/") {
        get{
            call.respondText("Hello World!")
        }
    }
    route("/reservation") {
        // get all reservations -> for admins maybe ? TODO add pagination

        get {
            call.respond(reservationService.getReservations())
        }

        // get single reservation TODO add pagination
        get("/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val uuid = UUID.fromString(id)
            // TODO validate uuid
            val reservation = reservationService.getReservationById(uuid) ?: return@get call.respondText(
                "Reservation not found",
                status = HttpStatusCode.NotFound
            )

            call.respond(reservation)
        }

        // add new reservation
        post {
            val reservation = call.receive<ReservationFullDto>()

            val createdReservation = reservationService.post(reservation) ?: return@post call.respondText(
                "Error while creating reservation",
                status = HttpStatusCode.InternalServerError // TODO check how to do it correctly
            )

            call.respond(HttpStatusCode.Created, createdReservation)
        }

        // edit existing reservation
        patch("/{id}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )

            val reservation = call.receive<ReservationFullDto>()

            val uuid = UUID.fromString(id)
            val updatedReservation = reservationService.patch(reservation) ?: return@patch call.respondText(
                "Error while creating reservation",
                status = HttpStatusCode.InternalServerError // TODO check
            )
        }

        // delete reservation
        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val uuid = UUID.fromString(id)
            reservationService.delete(uuid)
            call.respond(HttpStatusCode.NoContent)
        }

    }
}