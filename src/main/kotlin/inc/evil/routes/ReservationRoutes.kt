package inc.evil.routes

import io.ktor.server.routing.*

fun Route.reservationRoutes() {
    route("/reservation") {
        // get all reservations -> for admins maybe ? TODO add pagination
        get {

        }

        // get single reservation TODO add pagination
        get ("/{id}"){

        }

        // add new reservation
        post {

        }

        // edit existing reservation
        patch("/{id}"){

        }

        // delete reservation
        delete("/{id}"){

        }

    }
}