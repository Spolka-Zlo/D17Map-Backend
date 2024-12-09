package inc.evil.d17map.runners

import inc.evil.d17map.entities.*
import inc.evil.d17map.enums.ReservationType
import inc.evil.d17map.enums.Role
import inc.evil.d17map.repositories.*
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
    private val reservationRepository: ReservationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val buildingRepository: BuildingRepository,
    private val floorRepository: FloorRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val equipments = listOf(
            Equipment(name="Komputery"),
            Equipment(name="Routery"),
            Equipment(name="Projektor"),
            Equipment(name="Tablica")
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
            // 1 floor
            Classroom(
                name = "1.4",
                description = "Dziekanat",
                capacity = 2,
                modelKey = "14",
                equipments = mutableSetOf(equipments[0], equipments[1]),
                floor = floors[0]
            ),
            Classroom(
                name = "1.19",
                description = "sala 1.19 Lorem Ipsum",
                capacity = 30,
                modelKey = "119",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[0]
            ),
            Classroom(
                name = "1.20",
                description = "sala 1.20 dolor sit amet consectetur",
                capacity = 30,
                modelKey = "120",
                equipments = mutableSetOf(equipments[0], equipments[3]),
                floor = floors[0]
            ),
            Classroom(
                name = "1.36",
                description = "sala obok bufetu, nie wiem po co to komu",
                capacity = 40,
                modelKey = "136",
                equipments = mutableSetOf(equipments[0], equipments[3]),
                floor = floors[0]
            ),
            Classroom(
                name = "1.38",
                description = "sala z dużym projektorze, fajne miejsce na WDI",
                capacity = 120,
                modelKey = "138",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[0]
            ),
            // 2 floor
            Classroom(
                name = "2.41",
                description = "fajna sala na egzaminy, dużo się tu dzieje",
                capacity = 100,
                modelKey = "241",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[1]
            ),
            // 3 floor
            Classroom(
                name = "3.22",
                description = "Pracownia elektroniczna",
                capacity = 12,
                modelKey = "322",
                equipments = mutableSetOf(equipments[0], equipments[1], equipments[2]),
                floor = floors[2]
            ),
            Classroom(
                name = "3.23",
                description = "Pracownia elektroniczna v2",
                capacity = 12,
                modelKey = "323",
                equipments = mutableSetOf(equipments[0], equipments[1], equipments[2]),
                floor = floors[2]
            ),
            Classroom(
                name = "3.27a",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327a",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2]
            ),
            Classroom(
                name = "3.27b",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327b",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2]
            ),
            Classroom(
                name = "3.27c",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327c",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2]
            ),
            Classroom(
                name = "3.27d",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327d",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2]
            ),
            Classroom(
                name = "3.27e",
                description = "Sala z komputerami",
                capacity = 12,
                modelKey = "327e",
                equipments = mutableSetOf(equipments[0], equipments[2], equipments[3]),
                floor = floors[2]
            ),
            Classroom(
                name = "4.22",
                description = "sieci sieci sieci i inne takie fajne",
                capacity = 16,
                modelKey = "423",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.23",
                description = "sieci sieci sieci i inne takie fajne v2",
                capacity = 16,
                modelKey = "427",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.26",
                description = "fajna sala na chmury",
                capacity = 20,
                modelKey = "426",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.27",
                description = "fajna sala na funkcjyjne",
                capacity = 20,
                modelKey = "427",
                equipments = mutableSetOf(equipments[2]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.28",
                description = "fajna sala taka o",
                capacity = 20,
                modelKey = "428",
                equipments = mutableSetOf(equipments[3]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.28",
                description = "fajna sala taka o",
                capacity = 20,
                modelKey = "428",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.29",
                description = "fajna sala taka o v2",
                capacity = 20,
                modelKey = "429",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.30",
                description = "fajna sala taka o v3",
                capacity = 20,
                modelKey = "431",
                equipments = mutableSetOf(equipments[1], equipments[3]),
                floor = floors[3]
            ),
            Classroom(
                name = "4.31",
                description = "fajna sala taka o v4",
                capacity = 20,
                modelKey = "430",
                equipments = mutableSetOf(equipments[1] , equipments[2]),
                floor = floors[3]
            ),
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
                name = "WC",
                description = "damskie",
                modelKey = "E9",
                type = "WC",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "WC",
                description = "dla niepełnosprawnych",
                modelKey = "E8",
                type = "WC",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Winda",
                description = "",
                modelKey = "E16",
                type = "ELEVATOR",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Klatka schodowa",
                description = "",
                modelKey = "E11",
                type = "STAIRCASE",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Pokój prodziekana Marka Gajęckiego",
                description = "",
                modelKey = "123",
                type = "OTHER",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Dziekanat",
                description = "",
                modelKey = "14",
                type = "OTHER",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Stołówka",
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
