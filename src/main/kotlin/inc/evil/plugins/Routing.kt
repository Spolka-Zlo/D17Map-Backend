package inc.evil.plugins

import inc.evil.routes.classroomRoutes
import inc.evil.routes.reservationRoutes
import inc.evil.routes.userRoutes
import inc.evil.service.ClassroomService
import inc.evil.service.ReservationService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val classroomService : ClassroomService by inject()
    val reservationService : ReservationService by inject()

    routing {
        classroomRoutes(classroomService)
        reservationRoutes(reservationService)
        userRoutes(reservationService)
    }
}
