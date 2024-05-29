package inc.evil

import inc.evil.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
//    install(Resources) // for future maybe
    DatabaseSingleton.init()
    configureDI()
    configureRouting()
    configureSerialization()



}

