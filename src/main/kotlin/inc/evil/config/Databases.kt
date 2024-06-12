package inc.evil.config

import inc.evil.persistance.entities.*
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
            SchemaUtils.create(Users)
            SchemaUtils.create(Equipments)
            SchemaUtils.create(Classrooms)
            SchemaUtils.create(Reservations)
            SchemaUtils.create(ClassroomsEquipments)
        }
    }
}