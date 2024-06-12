package inc.evil.config


import inc.evil.persistance.repositories.ClassroomRepository
import inc.evil.persistance.repositories.EquipmentRepository
import inc.evil.persistance.repositories.ReservationRepository
import inc.evil.persistance.repositories.UserRepository
import inc.evil.persistance.repositories.impl.ClassroomRepositoryImpl
import inc.evil.persistance.repositories.impl.EquipmentRepositoryImpl
import inc.evil.persistance.repositories.impl.ReservationRepositoryImpl
import inc.evil.persistance.repositories.impl.UserRepositoryImpl
import inc.evil.service.ClassroomService
import inc.evil.service.EquipmentService
import inc.evil.service.ReservationService
import inc.evil.service.UserService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val appModules = module {
    // repositories
    single<ClassroomRepository> { ClassroomRepositoryImpl() }
    single<EquipmentRepository> { EquipmentRepositoryImpl() }
    single<ReservationRepository> { ReservationRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }

    // services
    single<ReservationService> {ReservationService(get())}
    single<ClassroomService> {ClassroomService(get())}
    single<EquipmentService> {EquipmentService()}
    single<UserService> {UserService()}
}


fun Application.configureDI() {
    install(Koin) {
        modules(appModules)
    }
}