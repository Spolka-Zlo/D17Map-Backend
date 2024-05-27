package inc.evil

import inc.evil.plugins.configureDI
import inc.evil.plugins.configureRouting
import inc.evil.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
//    install(Resources) // for future maybe
    configureDI()
    configureRouting()
    configureSerialization()
    // configureDatabases()


}

