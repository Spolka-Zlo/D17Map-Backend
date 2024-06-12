package inc.evil.service

import inc.evil.dto.UserPostDto
import inc.evil.persistance.entities.User
import org.jetbrains.exposed.sql.transactions.transaction

class UserService {
    fun createUser(user: UserPostDto) = transaction {
        val dbUser = User.new {
            email = user.email
            password = "*******" // TODO introduce authentication JWT etc.
            role = user.role
        }

        UserPostDto(dbUser.id.value, dbUser.email, dbUser.password, dbUser.role)
    }
}