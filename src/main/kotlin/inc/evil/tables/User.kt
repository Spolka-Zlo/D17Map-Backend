package inc.evil.tables

import inc.evil.enums.UserType
import org.jetbrains.exposed.sql.Table

object User : Table() {
    val id = uuid("id").uniqueIndex()
    val password = text("password")
    val email = text("email")
    val role = enumerationByName("role", 10, UserType::class)
}