package inc.evil

import inc.evil.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


// przykład tylko, ale działający u mnie ;>
fun Application.module() {
//    Database.connect(
//            "jdbc:postgresql://localhost:5432/d17_map",
//            driver = "org.postgresql.Driver",
//            user = "d17_map",
//            password = System.getenv("POSTGRES_PASSWORD")
//        )


//    configureSerialization()
//    configureDatabases()
    configureRouting()
}

