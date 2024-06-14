@file:Suppress("PropertyName")

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val postgresql_driver_version: String by project
val exposed_version: String by project
val h2_version : String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "inc.evil"
version = "0.0.1"


application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}


dependencies {
    // Core Ktor
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-config-yaml:2.3.10")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Dependency injection
    implementation("io.insert-koin:koin-ktor:3.5.6")
    implementation("io.insert-koin:koin-core:3.5.6")

    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

    // Postgres
    implementation("org.postgresql:postgresql:$postgresql_driver_version")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

    // Swagger UI
    implementation("io.ktor:ktor-server-swagger:$ktor_version")

    // CORS
    implementation("io.ktor:ktor-server-cors:$ktor_version")

    // Tests
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


}

sourceSets {
    main {
        kotlin {
            exclude("/home/amezydlo/AGH/sem6/PP/D17Map/D17Map-Backend/src/main/kotlin/inc/evil/CelePogladowe.kt")
        }
    }
}
