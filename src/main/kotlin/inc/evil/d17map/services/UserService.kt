package inc.evil.d17map.services

import inc.evil.d17map.dtos.ReservationResponse
import inc.evil.d17map.dtos.UserRequest
import inc.evil.d17map.dtos.UserResponse
import inc.evil.d17map.entities.User
import inc.evil.d17map.mappers.ReservationMapper
import inc.evil.d17map.repositories.ReservationRepository
import inc.evil.d17map.repositories.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository
) {
    fun createUser(userRequest: UserRequest): UserResponse {
        val user = User(
            email = userRequest.email,
            password = userRequest.password,
            userType = userRequest.userType
        )

        val savedUser = userRepository.save(user)

        return UserResponse(
            id = savedUser.id!!,
            email = savedUser.email,
            userType = savedUser.userType
        )
    }

    fun getUserReservations(userId: UUID): List<ReservationResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User with id '$userId' not found") }

        return user.reservations.map { ReservationMapper.mapToReservationResponse(it) }
    }

    fun getUserFutureReservations(userId: UUID): List<ReservationResponse> {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("User with id '$userId' not found") }

        val futureReservations = user.reservations.filter { it.date.isAfter(LocalDate.now()) }
        return futureReservations.map { ReservationMapper.mapToReservationResponse(it) }
    }
}
