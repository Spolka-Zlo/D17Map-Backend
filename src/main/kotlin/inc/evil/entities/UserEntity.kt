package inc.evil.entities

import inc.evil.tables.User
import inc.evil.enums.UserType
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class UserEntity(
    val id: UUID,
    val password: String,
    val email: String,
    val role: UserType
) {
    companion object {
        fun fromResultRow(row: ResultRow): UserEntity {
            return UserEntity(
                id = row[User.id].value,
                password = row[User.password],
                email = row[User.email],
                role = UserType.valueOf(row[User.role].toString())
            )
        }
    }
}
