package inc.evil.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.helloRoute() {
    route("/") {
        get{
            call.respondText("Hello World!")
        }
    }
}