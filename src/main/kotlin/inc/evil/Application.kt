package inc.evil

import inc.evil.config.DatabaseSingleton
import inc.evil.config.configureDI
import inc.evil.config.configureRouting
import inc.evil.config.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    DatabaseSingleton.init()
    DatabaseSingleton.drop()
    DatabaseSingleton.create()
    configureDI()
    configureRouting()
    configureSerialization()

}

