package inc.evil.tables

import inc.evil.enums.UserType
import org.jetbrains.exposed.dao.id.UUIDTable

object User : UUIDTable() {
    val password = text("password")
    val email = text("email")
    val role = enumerationByName("role", 10, UserType::class)
}
