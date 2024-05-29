package inc.evil.plugins

import inc.evil.tables.Classroom
import inc.evil.tables.ClassroomDetails
import inc.evil.tables.Reservation
import inc.evil.tables.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseSingleton {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://db:5432/d17_map"
        val user = "d17_map"
        val password = System.getenv("POSTGRES_PASSWORD")
        val database = Database.connect(jdbcURL, driverClassName, user, password)

        transaction(database) {
            SchemaUtils.create(User)
            SchemaUtils.create(ClassroomDetails)
            SchemaUtils.create(Classroom)
            SchemaUtils.create(Reservation)
        }
    }
}