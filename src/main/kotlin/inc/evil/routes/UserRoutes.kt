package inc.evil.routes

import inc.evil.dto.UserPostDto
import inc.evil.service.ReservationService
import inc.evil.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(
    userService: UserService,
    reservationService: ReservationService
) {

    route("/users") {
        get("/{id}/reservations") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed userid",
                status = HttpStatusCode.BadRequest
            )

            val reservations = reservationService.getUserReservations(id)
            call.respond(HttpStatusCode.OK, reservations)


        }

        get("/{id}/future-reservations") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed userid",
                status = HttpStatusCode.BadRequest
            )

            val reservations = reservationService.getUserFutureReservations(id)
            call.respond(HttpStatusCode.OK, reservations)
        }

        post {
            val userPostDto = call.receive<UserPostDto>()
            // TODO validate
            val createdUser = userService.createUser(userPostDto)
            call.respond(HttpStatusCode.Created, createdUser)
        }
    }
}