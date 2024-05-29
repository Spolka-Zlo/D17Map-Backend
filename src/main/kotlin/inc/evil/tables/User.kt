package inc.evil.tables

import inc.evil.enums.UserType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object Users : UUIDTable() {
    val password = text("password")
    val email = text("email")
    val role = enumerationByName("role", 10, UserType::class)
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var password by Users.password
    var email by Users.email
    var role by Users.role
}
