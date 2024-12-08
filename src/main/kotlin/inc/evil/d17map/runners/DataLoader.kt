package inc.evil.d17map.runners

import inc.evil.d17map.entities.*
import inc.evil.d17map.enums.ReservationType
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.EquipmentRepository
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.authorization.Permission
import inc.evil.d17map.security.authorization.PermissionRepository
import inc.evil.d17map.security.authorization.Role
import inc.evil.d17map.security.authorization.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime

@Component
class DataLoader(
    private val equipmentRepository: EquipmentRepository,
    private val classroomRepository: ClassroomRepository,
    private val extraRoomRepository: ExtraRoomRepository,
    private val userRepository: UserRepository,
    private val permissionRepository: PermissionRepository,
    private val roleRepository: RoleRepository,
    private val reservationRepository: ReservationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val buildingRepository: BuildingRepository,
    private val floorRepository: FloorRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val permissions = listOf(
            Permission(name = "VIEW_RES"),
            Permission(name = "RES_1_Floor"),
            Permission(name = "RES_2_Floor"),
            Permission(name = "RES_3_Floor"),
        )

        permissionRepository.saveAll(permissions)



        val roles = listOf(
            Role(name = "ROLE_STUDENT", permissions = mutableSetOf(permissions[0])),
            Role(name = "ROLE_TEACHER", permissions = mutableSetOf(permissions[1], permissions[2]))
        )

        roleRepository.saveAll(roles)


        val equipments = listOf(
            Equipment(name="COMPUTERS"),
            Equipment(name="ROUTERS"),
            Equipment(name="PROJECTOR"),
            Equipment(name="BOARD")
        )
        equipmentRepository.saveAll(equipments)

        val building = Building(name = "D17")
        buildingRepository.save(building)

        val floors = listOf(
            Floor(name = "1", building = building),
            Floor(name = "2", building = building),
            Floor(name = "3", building = building),
            Floor(name = "4", building = building)
        )
        floorRepository.saveAll(floors)


        val classrooms = listOf(
            Classroom(
                name = "2.41",
                description = "fajna sala na egzaminy, dużo się tu dzieje",
                capacity = 100,
                modelKey = "241",
                equipments = mutableSetOf(equipments[2]),
                floor = floors[1]
            ),
            Classroom(
                name = "4.27",
                description = "sieci sieci sieci i inne takie fajne",
                capacity = 115,
                modelKey = "427",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3]
            ),
            Classroom(
                name = "3.31",
                description = "obiektowe zwierzaki ewoluują w tej sali",
                capacity = 120,
                modelKey = "331",
                equipments = mutableSetOf(equipments[0]),
                floor = floors[2]
            ),
            Classroom(
                name = "1.38",
                description = "tutaj stało się wszystko",
                capacity = 120,
                modelKey = "138",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[0]
            )
        )
        classroomRepository.saveAll(classrooms)

        val extraRooms = listOf(
            ExtraRoom(
                name = "WC",
                description = "męskie",
                modelKey = "E7",
                type = "WC",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Sala do nauki",
                description = "Stołówka studencka",
                modelKey = "133",
                type = "OTHER",
                floor = floors[0]
            ),
        )
        extraRoomRepository.saveAll(extraRooms)

        val user = User(
            email = "admin",
            password = passwordEncoder.encode("admin"),
            userType = Role.STUDENT,
        )

        val users = listOf(
             user,
             User(
                email = "admin@admin.agh.edu.pl",
                password = passwordEncoder.encode("admin@password1234"),
                userType = Role.ADMIN,
            )
        )

        userRepository.saveAll(users)

        val reservations = listOf(
            Reservation(
                title = "Egzamin Algebra",
                description = "Egzamin końcowy z algebry",
                date = LocalDate.of(2024, 12, 15),
                startTime = LocalTime.of(9, 0),
                endTime = LocalTime.of(12, 0),
                classroom = classrooms[0],
                user = user,
                type = ReservationType.EXAM,
                numberOfParticipants = 100
            ),
            Reservation(
                title = "Warsztaty Sieciowe",
                description = "Warsztaty z konfiguracji routerów",
                date = LocalDate.of(2024, 11, 20),
                startTime = LocalTime.of(14, 0),
                endTime = LocalTime.of(17, 0),
                classroom = classrooms[1],
                user = user,
                type = ReservationType.STUDENTS_CLUB_MEETING,
                numberOfParticipants = 50
            ),
            Reservation(
                title = "Zajęcia z Java",
                description = "Zajęcia z programowania w Javie",
                date = LocalDate.of(2024, 10, 25),
                startTime = LocalTime.of(10, 0),
                endTime = LocalTime.of(13, 0),
                classroom = classrooms[2],
                user = user,
                type = ReservationType.LECTURE,
                numberOfParticipants = 120
            ),
            Reservation(
                title = "Spotkanie koła naukowego",
                description = "Spotkanie koła naukowego programistów",
                date = LocalDate.of(2024, 11, 10),
                startTime = LocalTime.of(16, 0),
                endTime = LocalTime.of(18, 0),
                classroom = classrooms[2],
                user = user,
                type = ReservationType.STUDENTS_CLUB_MEETING,
                numberOfParticipants = 30
            ),
            Reservation(
                title = "Konferencja IT",
                description = "Prezentacja IT",
                date = LocalDate.of(2024, 12, 5),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(15, 0),
                classroom = classrooms[0],
                user = user,
                type = ReservationType.EVENT,
                numberOfParticipants = 80
            )
        )
        reservationRepository.saveAll(reservations)
    }
}
