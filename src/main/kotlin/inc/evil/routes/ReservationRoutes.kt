package inc.evil.routes

import inc.evil.dto.ReservationPostDto
import inc.evil.service.ReservationService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.reservationRoutes(reservationService: ReservationService) {

    route("/reservations") {

        /*
        * [{
        *    "id": 1234,
        *    "type": "TEST",
        *    "startTime": "21:15",
        *    "endTime": "22:30",
        *    "classroom":
        *      {
        *        "id": 1345,
        *        "name": "3.12"
        *      }
        *   }, ...
        * ]
        *
        * */
        get {
            val day = call.request.queryParameters["day"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Query parameter 'date' must be specified"
            )

            val reservations = reservationService.getGivenDayReservations(day)
            call.respond(HttpStatusCode.OK, reservations)
        }


        /*
        *  {
        *
        *    "name": "Moja rezerwacja",
        *    "type": "TEST",
        *    "userId": 1234,
        *    "date": "2024-06-22"
        *    "startTime": "22:15",
        *    "endTime": "23:45"
        *    "classroomId": 1234
        *
        *  }
        * */

        post {
            val reservationRequest = call.receive<ReservationPostDto>()
            val reservationResponse = reservationService.createReservation(reservationRequest)
            call.respond(HttpStatusCode.Created, reservationResponse)
        }

    }
}