package inc.evil.plugins

import inc.evil.routes.classroomRoutes
import inc.evil.service.ClassroomService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val classroomService : ClassroomService by inject()


    routing {
        classroomRoutes(classroomService)


        //customerRouting()
        //listOrderRoute()
        //getOrderRoute()
        //totalizeOrderRoute()
    }
}
