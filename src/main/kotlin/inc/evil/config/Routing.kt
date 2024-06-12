package inc.evil.config

import inc.evil.routes.*
import inc.evil.service.ClassroomService
import inc.evil.service.EquipmentService
import inc.evil.service.ReservationService
import inc.evil.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val classroomService : ClassroomService by inject()
    val reservationService : ReservationService by inject()
    val equipmentService : EquipmentService by inject()
    val userService : UserService by inject()

    routing {
        helloRoute()
        classroomRoutes(classroomService)
        reservationRoutes(reservationService)
        userRoutes(userService, reservationService)
        equipmentRoutes(equipmentService)
    }
}
