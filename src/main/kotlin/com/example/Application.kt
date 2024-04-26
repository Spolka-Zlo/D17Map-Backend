package com.example


import com.example.plugins.configureDatabases
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 8080) {
        Database.connect(
            "jdbc:postgresql://localhost:5432/d17_map",
            driver = "org.postgresql.Driver",
            user = "d17_map",
            password = System.getenv("POSTGRES_PASSWORD")
        )
        configureRouting()

    }.start(wait = true)
}


fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureRouting()
}
