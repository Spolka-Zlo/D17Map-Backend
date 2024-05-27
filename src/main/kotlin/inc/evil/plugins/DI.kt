package inc.evil.plugins

import inc.evil.dao.ClassroomDAO
import inc.evil.service.ClassroomService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val appModules = module {
    single<ClassroomDAO> { ClassroomDAO() }
    single<ClassroomService> { ClassroomService(get()) }
}


fun Application.configureDI() {
    install(Koin) {
        modules(appModules)
    }
}