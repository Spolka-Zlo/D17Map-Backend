package inc.evil.d17map.services

import inc.evil.d17map.dtos.ReservationDto
import inc.evil.d17map.dtos.UserDto
import inc.evil.d17map.entities.User
import inc.evil.d17map.mappers.Mapper
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*

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
