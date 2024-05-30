package inc.evil.persistance.entities

import inc.evil.enums.UserType
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object Users : UUIDTable() {
    val email = text("email")
    val password = text("password")
    val role = enumerationByName("role", 10, UserType::class)
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var email by Users.email
    var password by Users.password
    var role by Users.role
    val reservations by Reservation referrersOn Reservations.user
}
