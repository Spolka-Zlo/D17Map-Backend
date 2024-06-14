package inc.evil

import inc.evil.config.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    DatabaseSingleton.init()
    DatabaseSingleton.drop()
    DatabaseSingleton.create()
    configureCors()
    configureDI()
    configureRouting()
    configureSerialization()
    generateTestData()
}

