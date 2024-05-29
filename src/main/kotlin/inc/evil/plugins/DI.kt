package inc.evil.plugins


import inc.evil.service.ClassroomService
import inc.evil.service.ReservationService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val appModules = module {
//    single<ClassroomDAO> { ClassroomDAO() }
    single<ClassroomService> { ClassroomService() }

//    single<ReservationDAO> { ReservationDAO() }
    single<ReservationService> { ReservationService() }
}


fun Application.configureDI() {
    install(Koin) {
        modules(appModules)
    }
}