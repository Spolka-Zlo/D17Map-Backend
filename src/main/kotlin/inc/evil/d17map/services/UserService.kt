package inc.evil.d17map.services

import inc.evil.d17map.dtos.UserDto
import inc.evil.d17map.entities.User
import inc.evil.d17map.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun createUser(userDto: UserDto): UserDto {
        val user = userDto.password?.let {
            User(
                email = userDto.email,
                password = it,
                userType = userDto.userType
            )
        }

        val savedUser = userRepository.save(user!!)

        return UserDto(
            id = savedUser.id!!,
            email = savedUser.email,
            userType = savedUser.userType
        )
    }
}
