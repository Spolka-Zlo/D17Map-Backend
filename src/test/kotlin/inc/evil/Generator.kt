package inc.evil
//
//import io.ktor.client.*
//import io.ktor.client.engine.cio.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import kotlinx.coroutines.runBlocking
//
//fun generateTestData() = runBlocking {
//    val client = HttpClient(CIO)
//
//    val classrooms = listOf(
//        """
//        {
//            "name": "2.01",
//            "description": "Advanced Math Classroom",
//            "capacity": 30,
//            "equipment": ["Projektor", "Komputery", "Terminale"]
//        }
//        """,
//        """
//        {
//            "name": "5.05",
//            "description": "Physics Lab",
//            "capacity": 20,
//            "equipment": ["Projektor", "Komputery", "Terminale"]
//        }
//        """,
//        """
//        {
//            "name": "1.10",
//            "description": "Chemistry Lab",
//            "capacity": 25,
//            "equipment": ["Projektor", "Komputery", "Terminale"]
//        }
//        """,
//        """
//        {
//            "name": "3.14",
//            "description": "Sala Garka Maruchy",
//            "capacity": 43,
//            "equipment": ["Projektor", "Komputery", "Terminale"]
//        }
//        """,
//        """
//        {
//            "name": "4.23",
//            "description": "Sala sieciowa",
//            "capacity": 12,
//            "equipment": ["Projektor", "Komputery", "Terminale", "Routery", "Kable"]
//        }
//        """,
//        """
//        {
//            "name": "1.38",
//            "description": "Sala Maga",
//            "capacity": 250,
//            "equipment": ["Projektor", "Kable", "Inne"]
//        }
//        """,
//        """
//        {
//            "name": "2.49",
//            "description": "Sala Wszystkiego Najlepszego",
//            "capacity": 30,
//            "equipment": ["Kable", "Inne"]
//        }
//        """,
//        """
//        {
//            "name": "6.66",
//            "description": "Sala 666",
//            "capacity": 66,
//            "equipment": ["Projektor", "Routery"]
//        }
//        """,
//        """
//        {
//            "name": "1.23",
//            "description": "Sala 123",
//            "capacity": 123,
//            "equipment": ["Projektor", "Komputery", "Terminale"]
//        }
//        """,
//        """
//        {
//            "name": "3.33",
//            "description": "Sala 33",
//            "capacity": 33,
//            "equipment": ["Komputery", "Inne"]
//        }
//        """,
//        """
//        {
//            "name": "3.57",
//            "description": "Sala Dla I.",
//            "capacity": 8,
//            "equipment": ["Inne"]
//        }
//        """,
//    )
//
//    classrooms.forEach { classroom ->
//        client.post("http://localhost:8080/classrooms") {
//            contentType(ContentType.Application.Json)
//            setBody(classroom)
//        }
//    }
//
//    val equipments = listOf(
//        """{"name": "Projektor"}""",
//        """{"name": "Komputery"}"""
//    )
//
//    equipments.forEach { equipment ->
//        client.post("http://localhost:8080/equipments") {
//            contentType(ContentType.Application.Json)
//            setBody(equipment)
//        }
//    }
//
//    val equipmentBatch = listOf("Terminale", "Routery", "Kable")
//
//    client.post("http://localhost:8080/equipments/batch") {
//        contentType(ContentType.Application.Json)
//        setBody(equipmentBatch)
//    }
//
//    val users = listOf(
//        """{"email": "john.doe@agh.edu.pl", "password": "Password123", "role": "TEACHER"}""",
//        """{"email": "jane.doe@student.agh.edu.pl", "password": "MySecretPassword", "role": "STUDENT"}""",
//        """{"email": "alice.smith@agh.edu.pl", "password": "AnotherPass", "role": "TEACHER"}""",
//        """{"email": "bob.jones@student.agh.edu.pl", "password": "BobSecret", "role": "STUDENT"}""",
//        """{"email": "student1@student.agh.edu.pl", "password": "Student1Secret", "role": "STUDENT"}"""
//    )
//
//    users.forEach { user ->
//        client.post("http://localhost:8080/users") {
//            contentType(ContentType.Application.Json)
//            setBody(user)
//        }
//    }
//
//    val reservations = listOf(
//        """
//        {
//            "title": "Morning Lecture",
//            "type": "LECTURE",
//            "userId": "john.doe@agh.edu.pl",
//            "date": "2024-06-22",
//            "startTime": "08:00",
//            "endTime": "10:00",
//            "classroomId": "1.38"
//        }
//        """,
//        """
//        {
//            "title": "Physics Lab",
//            "type": "LAB",
//            "userId": "alice.smith@agh.edu.pl",
//            "date": "2024-06-23",
//            "startTime": "10:00",
//            "endTime": "12:00",
//            "classroomId": "5.05"
//        }
//        """,
//        """
//        {
//            "title": "Chemistry Workshop",
//            "type": "WORKSHOP",
//            "userId": "john.doe@agh.edu.pl",
//            "date": "2024-06-24",
//            "startTime": "13:00",
//            "endTime": "15:00",
//            "classroomId": "1.10"
//        }
//        """,
//        """
//        {
//            "title": "Networking Seminar",
//            "type": "SEMINAR",
//            "userId": "alice.smith@agh.edu.pl",
//            "date": "2024-06-25",
//            "startTime": "09:00",
//            "endTime": "11:00",
//            "classroomId": "4.23"
//        }
//        """,
//        """
//        {
//            "title": "Math Study Group",
//            "type": "STUDY_GROUP",
//            "userId": "jane.doe@student.agh.edu.pl",
//            "date": "2024-06-22",
//            "startTime": "14:00",
//            "endTime": "16:00",
//            "classroomId": "2.01"
//        }
//        """,
//        """
//        {
//            "title": "Physics Study Group",
//            "type": "STUDY_GROUP",
//            "userId": "bob.jones@student.agh.edu.pl",
//            "date": "2024-06-23",
//            "startTime": "14:00",
//            "endTime": "16:00",
//            "classroomId": "5.05"
//        }
//        """,
//        """
//        {
//            "title": "Evening Chemistry Lab",
//            "type": "LAB",
//            "userId": "jane.doe@student.agh.edu.pl",
//            "date": "2024-06-24",
//            "startTime": "17:00",
//            "endTime": "19:00",
//            "classroomId": "1.10"
//        }
//        """,
//        """
//        {
//            "title": "Python Programming Workshop",
//            "type": "WORKSHOP",
//            "userId": "john.doe@agh.edu.pl",
//            "date": "2024-06-22",
//            "startTime": "11:00",
//            "endTime": "13:00",
//            "classroomId": "2.49"
//        }
//        """,
//        """
//        {
//            "title": "AI Seminar",
//            "type": "SEMINAR",
//            "userId": "alice.smith@agh.edu.pl",
//            "date": "2024-06-23",
//            "startTime": "12:00",
//            "endTime": "14:00",
//            "classroomId": "6.66"
//        }
//        """,
//        """
//        {
//            "title": "Data Structures Lecture",
//            "type": "LECTURE",
//            "userId": "john.doe@agh.edu.pl",
//            "date": "2024-06-24",
//            "startTime": "09:00",
//            "endTime": "11:00",
//            "classroomId": "1.23"
//        }
//        """,
//        """
//        {
//            "title": "Cybersecurity Workshop",
//            "type": "WORKSHOP",
//            "userId": "alice.smith@agh.edu.pl",
//            "date": "2024-06-25",
//            "startTime": "10:00",
//            "endTime": "12:00",
//            "classroomId": "4.20"
//        }
//        """,
//        """
//        {
//            "title": "Web Development Bootcamp",
//            "type": "BOOTCAMP",
//            "userId": "bob.jones@student.agh.edu.pl",
//            "date": "2024-06-22",
//            "startTime": "15:00",
//            "endTime": "18:00",
//            "classroomId": "3.33"
//        }
//        """,
//        """
//        {
//            "title": "Database Systems Lecture",
//            "type": "LECTURE",
//            "userId": "john.doe@agh.edu.pl",
//            "date": "2024-06-23",
//            "startTime": "08:00",
//            "endTime": "10:00",
//            "classroomId": "2.01"
//        }
//        """,
//        """
//        {
//            "title": "Art Workshop",
//            "type": "WORKSHOP",
//            "userId": "alice.smith@agh.edu.pl",
//            "date": "2024-06-26",
//            "startTime": "14:00",
//            "endTime": "16:00",
//            "classroomId": "1.23"
//        }
//        """,
//        """
//        {
//            "title": "Mobile App Development Seminar",
//            "type": "SEMINAR",
//            "userId": "bob.jones@student.agh.edu.pl",
//            "date": "2024-06-27",
//            "startTime": "11:00",
//            "endTime": "13:00",
//            "classroomId": "2.49"
//        }
//        """,
//        """
//        {
//            "title": "Photography Workshop",
//            "type": "WORKSHOP",
//            "userId": "jane.doe@student.agh.edu.pl",
//            "date": "2024-06-28",
//            "startTime": "15:00",
//            "endTime": "17:00",
//            "classroomId": "5.05"
//        }
//        """,
//        """
//        {
//            "title": "Machine Learning Lecture",
//            "type": "LECTURE",
//            "userId": "john.doe@agh.edu.pl",
//            "date": "2024-06-26",
//            "startTime": "09:00",
//            "endTime": "11:00",
//            "classroomId": "6.66"
//        }
//        """
//    )
//
//    reservations.forEach { reservation ->
//        client.post("http://localhost:8080/reservations") {
//            contentType(ContentType.Application.Json)
//            setBody(reservation)
//        }
//    }
//
//    client.close()
//}
