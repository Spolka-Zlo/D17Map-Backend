package inc.evil.d17map.runners

import inc.evil.d17map.entities.*
import inc.evil.d17map.enums.RecurringType
import inc.evil.d17map.enums.ReservationType
import inc.evil.d17map.repositories.*
import inc.evil.d17map.security.authorization.Role
import inc.evil.d17map.security.authorization.RoleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Component
class DataLoader(
    private val equipmentRepository: EquipmentRepository,
    private val classroomRepository: ClassroomRepository,
    private val extraRoomRepository: ExtraRoomRepository,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val reservationRepository: ReservationRepository,
    private val passwordEncoder: PasswordEncoder,
    private val buildingRepository: BuildingRepository,
    private val floorRepository: FloorRepository,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val roles = listOf(
            Role(name = "ROLE_STUDENT"),
            Role(name = "ROLE_TEACHER"),
            Role(name = "ROLE_ADMIN"),
        )

        roleRepository.saveAll(roles)


        val equipments = listOf(
            Equipment(name = "Komputery"),
            Equipment(name = "Routery"),
            Equipment(name = "Projektor"),
            Equipment(name = "Tablica")
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

        val photos = listOf(
            (ClassPathResource("photos/119.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/138.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/241.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/327.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/327d.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/327e.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/422.jpg").inputStream.readBytes()),
            (ClassPathResource("photos/426.jpg").inputStream.readBytes()),
        )


        val classrooms = listOf(
            // 1 floor
            Classroom(
                name = "1.19",
                description = "sala 1.19 Lorem Ipsum",
                capacity = 30,
                modelKey = "119",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[0],
                photo = photos[0]
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
                floor = floors[0],
                photo = photos[1]
            ),
            // 2 floor
            Classroom(
                name = "2.41",
                description = "fajna sala na egzaminy, dużo się tu dzieje",
                capacity = 100,
                modelKey = "241",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[1],
                photo = photos[2]
            ),
            // 3 floor
            Classroom(
                name = "3.13",
                description = "Idealny do spotkań z opiekunem",
                capacity = 12,
                modelKey = "322",
                equipments = mutableSetOf(equipments[0], equipments[1], equipments[2]),
                floor = floors[2]
            ),
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
                floor = floors[2],
                photo = photos[3]
            ),
            Classroom(
                name = "3.27b",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327b",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2],
                photo = photos[3]
            ),
            Classroom(
                name = "3.27c",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327c",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2],
                photo = photos[3]
            ),
            Classroom(
                name = "3.27d",
                description = "Sala dydaktyczna z tablcą",
                capacity = 30,
                modelKey = "327d",
                equipments = mutableSetOf(equipments[2], equipments[3]),
                floor = floors[2],
                photo = photos[4]
            ),
            Classroom(
                name = "3.27e",
                description = "Sala z komputerami",
                capacity = 12,
                modelKey = "327e",
                equipments = mutableSetOf(equipments[0], equipments[2], equipments[3]),
                floor = floors[2],
                photo = photos[5]
            ),
            Classroom(
                name = "4.22",
                description = "sieci sieci sieci i inne takie fajne",
                capacity = 16,
                modelKey = "422",
                equipments = mutableSetOf(equipments[1]),
                floor = floors[3],
                photo = photos[6]
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
                floor = floors[3],
                photo = photos[7]
            ),
            Classroom(
                name = "4.27",
                description = "fajna sala na funkcjyjne",
                capacity = 20,
                modelKey = "427",
                equipments = mutableSetOf(equipments[2]),
                floor = floors[3],
                photo = photos[7]
            ),
            Classroom(
                name = "4.28",
                description = "fajna sala taka o",
                capacity = 20,
                modelKey = "428",
                equipments = mutableSetOf(equipments[3]),
                floor = floors[3],
                photo = photos[7]
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
                equipments = mutableSetOf(equipments[1], equipments[2]),
                floor = floors[3]
            ),
        )
        classroomRepository.saveAll(classrooms)

        val extraRooms = listOf(
            // floor 1
            ExtraRoom(
                name = "WC męskie 1 poziom",
                description = "",
                modelKey = "E7",
                type = "WC",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "WC damskie 1 poziom",
                description = "",
                modelKey = "E9",
                type = "WC",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "WC dla niepełnosprawnych 1 poziom",
                description = "",
                modelKey = "E8",
                type = "WC",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Winda 1 poziom",
                description = "",
                modelKey = "E16",
                type = "Windy",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Klatka schodowa 1 poziom",
                description = "",
                modelKey = "E11",
                type = "Klatki schodowe",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Pokój prodziekana Marka Gajęckiego",
                description = "",
                modelKey = "123",
                type = "Inne",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Dziekanat",
                description = "",
                modelKey = "14",
                type = "Inne",
                floor = floors[0]
            ),
            ExtraRoom(
                name = "Stołówka",
                description = "Stołówka studencka",
                modelKey = "133",
                type = "Inne",
                floor = floors[0]
            ),
            // floor 2
            ExtraRoom(
                name = "WC męskie 2 poziom",
                description = "",
                modelKey = "245a",
                type = "WC",
                floor = floors[1]
            ),
            ExtraRoom(
                name = "WC damskie 2 poziom",
                description = "",
                modelKey = "244a",
                type = "WC",
                floor = floors[1]
            ),
            ExtraRoom(
                name = "WC dla niepełnosprawnych 2 poziom",
                description = "",
                modelKey = "246",
                type = "WC",
                floor = floors[1]
            ),
            ExtraRoom(
                name = "Winda 2 poziom",
                description = "",
                modelKey = "E18",
                type = "Windy",
                floor = floors[1]
            ),
            ExtraRoom(
                name = "Klatka schodowa 2 poziom",
                description = "",
                modelKey = "239",
                type = "Klatki schodowe",
                floor = floors[0]
            ),
            // floor 3
            ExtraRoom(
                name = "WC męskie 3 poziom",
                description = "",
                modelKey = "316a",
                type = "WC",
                floor = floors[2]
            ),
            ExtraRoom(
                name = "WC damskie 3 poziom",
                description = "damskie",
                modelKey = "317a",
                type = "WC",
                floor = floors[2]
            ),
            ExtraRoom(
                name = "WC dla niepełnospraawnych 3 poziom",
                description = "",
                modelKey = "315",
                type = "WC",
                floor = floors[2]
            ),
            ExtraRoom(
                name = "Winda 3 poziom",
                description = "",
                modelKey = "E19",
                type = "Windy",
                floor = floors[2]
            ),
            ExtraRoom(
                name = "Klatka schodowa 3 poziom",
                description = "",
                modelKey = "318",
                type = "Klatki schodowe",
                floor = floors[2]
            ),
            // floor 4
            ExtraRoom(
                name = "WC męskie 4 poziom",
                description = "",
                modelKey = "416a",
                type = "WC",
                floor = floors[3]
            ),
            ExtraRoom(
                name = "WC damskie 4 poziom",
                description = "",
                modelKey = "417a",
                type = "WC",
                floor = floors[3]
            ),
            ExtraRoom(
                name = "WC dla niepełnosprawnych 4 poziom",
                description = "",
                modelKey = "415",
                type = "WC",
                floor = floors[3]
            ),
            ExtraRoom(
                name = "Winda 4 poziom",
                description = "",
                modelKey = "E20",
                type = "Windy",
                floor = floors[3]
            ),
            ExtraRoom(
                name = "Klatka schodowa 4 poziom",
                description = "",
                modelKey = "418",
                type = "Klatki schodowe",
                floor = floors[3]
            ),
        )
        extraRoomRepository.saveAll(extraRooms)

        val admin = User(
            email = "admin",
            password = passwordEncoder.encode("admin"),
            roles = mutableSetOf(roles[2])
        )

        val teacher = User(
            email = "teacher@agh.edu.pl",
            password = passwordEncoder.encode("teacher"),
            roles = mutableSetOf(roles[1])
        )

        val student = User(
            email = "example@student.agh.edu.pl",
            password = passwordEncoder.encode("student"),
            roles = mutableSetOf(roles[0])
        )

        val users = listOf(
            admin,
            teacher,
            student,
        )

        userRepository.saveAll(users)

        val reservationUUID = UUID.randomUUID()

        val reservations = listOf(
            Reservation(
                title = "Egzamin Algebra",
                description = "Egzamin końcowy z algebry",
                date = LocalDate.of(2024, 12, 15),
                startTime = LocalTime.of(9, 0),
                endTime = LocalTime.of(12, 0),
                classroom = classrooms[0],
                user = teacher,
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
                user = teacher,
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
                user = teacher,
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
                user = teacher,
                type = ReservationType.STUDENTS_CLUB_MEETING,
                numberOfParticipants = 30
            ),
            Reservation(
                title = "Konferencja IT",
                description = "Prezentacja IT",
                date = LocalDate.of(2027, 12, 5),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(15, 0),
                classroom = classrooms[0],
                user = teacher,
                type = ReservationType.EVENT,
                numberOfParticipants = 80
            ),

            Reservation(
                title = "Analiza matematyczna",
                description = "Codzienne zajęcia z analizy matematycznej",
                date = LocalDate.of(2027, 1, 1),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(15, 0),
                classroom = classrooms[0],
                user = teacher,
                type = ReservationType.CLASS,
                numberOfParticipants = 80,
                recurringId = reservationUUID,
                recurringEndDate = LocalDate.of(2027, 1, 4),
                recurringType = RecurringType.DAILY
            ),
            Reservation(
                title = "Analiza matematyczna",
                description = "Codzienne zajęcia z analizy matematycznej",
                date = LocalDate.of(2027, 1, 2),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(15, 0),
                classroom = classrooms[0],
                user = teacher,
                type = ReservationType.CLASS,
                numberOfParticipants = 80,
                recurringId = reservationUUID,
                recurringEndDate = LocalDate.of(2027, 1, 4),
                recurringType = RecurringType.DAILY
            ),
            Reservation(
                title = "Analiza matematyczna",
                description = "Codzienne zajęcia z analizy matematycznej",
                date = LocalDate.of(2027, 1, 3),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(15, 0),
                classroom = classrooms[0],
                user = teacher,
                type = ReservationType.CLASS,
                numberOfParticipants = 80,
                recurringId = reservationUUID,
                recurringEndDate = LocalDate.of(2027, 1, 4),
                recurringType = RecurringType.DAILY
            ),
            Reservation(
                title = "Analiza matematyczna",
                description = "Codzienne zajęcia z analizy matematycznej",
                date = LocalDate.of(2027, 1, 4),
                startTime = LocalTime.of(12, 0),
                endTime = LocalTime.of(15, 0),
                classroom = classrooms[0],
                user = teacher,
                type = ReservationType.CLASS,
                numberOfParticipants = 80,
                recurringId = reservationUUID,
                recurringEndDate = LocalDate.of(2027, 1, 4),
                recurringType = RecurringType.DAILY
            )
        )
        reservationRepository.saveAll(reservations)
    }
}
