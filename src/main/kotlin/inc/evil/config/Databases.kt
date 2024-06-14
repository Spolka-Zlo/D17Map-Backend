package inc.evil.config

import inc.evil.enums.ReservationType
import inc.evil.persistance.entities.*
import org.jetbrains.exposed.sql.Database
import inc.evil.enums.UserType
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import java.time.LocalDate
import java.time.LocalTime

object DatabaseSingleton {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://db:5432/d17_map"
        val user = "d17_map"
        val password = System.getenv("POSTGRES_PASSWORD")
        Database.connect(jdbcURL, driverClassName, user, password)


    }

    fun create()  {
        transaction {
            SchemaUtils.create(Users)
            SchemaUtils.create(Equipments)
            SchemaUtils.create(Classrooms)
            SchemaUtils.create(Reservations)
            SchemaUtils.create(ClassroomsEquipments)
        }
    }

    fun drop() {
        transaction {
            SchemaUtils.drop(ClassroomsEquipments)
            SchemaUtils.drop(Reservations)
            SchemaUtils.drop(Classrooms)
            SchemaUtils.drop(Equipments)
            SchemaUtils.drop(Users)
        }
    }

    fun seedData() {
        transaction {
            Classroom.new {
                name = "2.01"
                description = "Advanced Math Classroom"
                capacity = 30
            }
            Classroom.new {
                name = "5.05"
                description = "Physics Lab"
                capacity = 20
            }
            Classroom.new {
                name = "1.10"
                description = "Chemistry Lab"
                capacity = 25
            }
            Classroom.new {
                name = "3.14"
                description = "Sala Garka Maruchy"
                capacity = 43
            }
            Classroom.new {
                name = "4.23"
                description = "Sala sieciowa"
                capacity = 12
            }
            Classroom.new {
                name = "1.38"
                description = "Sala Maga"
                capacity = 250
            }
            Classroom.new {
                name = "2.49"
                description = "Sala Wszystkiego Najlepszego"
                capacity = 30
            }
            Classroom.new {
                name = "6.66"
                description = "Sala 666"
                capacity = 66
            }
            Classroom.new {
                name = "1.23"
                description = "Sala 123"
                capacity = 123
            }
            Classroom.new {
                name = "3.33"
                description = "Sala 33"
                capacity = 33
            }
            Classroom.new {
                name = "3.57"
                description = "Sala Dla I."
                capacity = 8
            }
            Classroom.new {
                name = "4.04"
                description = "Sala Wielkiego Brata"
                capacity = 1984
            }
            Classroom.new {
                name = "6.7"
                description = "Sala 7, 6.7"
                capacity = 7
            }
            Classroom.new {
                name = "4.20"
                description = "Sala 20"
                capacity = 20
            }
            Classroom.new {
                name = "2.06"
                description = "Sala sześć 06"
                capacity = 60
            }
        }

        transaction {
            Equipment.new {
                name = "Projektor"
            }
            Equipment.new {
                name = "Komputery"
            }
            Equipment.new {
                name = "Terminale"
            }
            Equipment.new {
                name = "Kable"
            }
            Equipment.new {
                name = "Inne"
            }
        }

        transaction {
            User.new {
                email = "john.doe@agh.edu.pl"
                password = "Password123"
                role = UserType.TEACHER
            }

            User.new {
                email = "jane.doe@student.agh.edu.pl"
                password = "MySecretPassword"
                role = UserType.STUDENT
            }

            User.new {
                email = "alice.smith@agh.edu.pl"
                password = "AnotherPass"
                role = UserType.TEACHER
            }

            User.new {
                email = "bob.jones@student.agh.edu.pl"
                password = "BobSecret"
                role = UserType.STUDENT
            }

            User.new {
                email = "student1@student.agh.edu.pl"
                password = "Student1Secret"
                role = UserType.STUDENT
            }

        }

        transaction {
            listOf(
                Reservation.new {
                    title = "Morning Lecture"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-22")
                    startTime = LocalTime.parse("08:00")
                    endTime = LocalTime.parse("10:00")
                    type = ReservationType.LECTURE
                },
                Reservation.new {
                    title = "Physics Lab"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-23")
                    startTime = LocalTime.parse("10:00")
                    endTime = LocalTime.parse("12:00")
                    type = ReservationType.CLASS
                },
                Reservation.new {
                    title = "Chemistry Workshop"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-24")
                    startTime = LocalTime.parse("13:00")
                    endTime = LocalTime.parse("15:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "Networking"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-25")
                    startTime = LocalTime.parse("09:00")
                    endTime = LocalTime.parse("11:00")
                    type = ReservationType.CONFERENCE
                },
                Reservation.new {
                    title = "Math Study Group"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-22")
                    startTime = LocalTime.parse("14:00")
                    endTime = LocalTime.parse("16:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "Physics Study Group"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-23")
                    startTime = LocalTime.parse("14:00")
                    endTime = LocalTime.parse("16:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "Evening Chemistry Lab"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-24")
                    startTime = LocalTime.parse("17:00")
                    endTime = LocalTime.parse("19:00")
                    type = ReservationType.CLASS
                },
                Reservation.new {
                    title = "Python Programming Workshop"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-22")
                    startTime = LocalTime.parse("11:00")
                    endTime = LocalTime.parse("13:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "AI Exam"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-23")
                    startTime = LocalTime.parse("12:00")
                    endTime = LocalTime.parse("14:00")
                    type = ReservationType.EXAM
                },
                Reservation.new {
                    title = "Data Structures Consultations"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-24")
                    startTime = LocalTime.parse("09:00")
                    endTime = LocalTime.parse("11:00")
                    type = ReservationType.CONSULTATIONS
                },
                Reservation.new {
                    title = "Cybersecurity Workshop"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-25")
                    startTime = LocalTime.parse("10:00")
                    endTime = LocalTime.parse("12:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "Web Development Bootcamp"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-22")
                    startTime = LocalTime.parse("15:00")
                    endTime = LocalTime.parse("18:00")
                    type = ReservationType.EVENT
                },
                Reservation.new {
                    title = "Database Systems Lecture"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-23")
                    startTime = LocalTime.parse("08:00")
                    endTime = LocalTime.parse("10:00")
                    type = ReservationType.LECTURE
                },
                Reservation.new {
                    title = "Art Workshop"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-26")
                    startTime = LocalTime.parse("14:00")
                    endTime = LocalTime.parse("16:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "Mobile App Development Test"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-27")
                    startTime = LocalTime.parse("11:00")
                    endTime = LocalTime.parse("13:00")
                    type = ReservationType.TEST
                },
                Reservation.new {
                    title = "Photography Workshop"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-28")
                    startTime = LocalTime.parse("15:00")
                    endTime = LocalTime.parse("17:00")
                    type = ReservationType.STUDENTS_CLUB_MEETING
                },
                Reservation.new {
                    title = "Machine Learning Lecture"
                    user = User.findById(UUID.fromString("your-user-id-here"))!!
                    classroom = Classroom.findById(UUID.fromString("your-classroom-id-here"))!!
                    date = LocalDate.parse("2024-06-26")
                    startTime = LocalTime.parse("09:00")
                    endTime = LocalTime.parse("11:00")
                    type = ReservationType.LECTURE
                }
            )
        }
    }
}