package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.UserDto
import inc.evil.d17map.services.ReservationService
import inc.evil.d17map.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val reservationService: ReservationService
) {
    @GetMapping("/{id}/future-reservations")
    fun getUserFutureReservations(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        return try {
            val futureReservations = reservationService.getUserFutureReservations(id)
            ResponseEntity(futureReservations, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity("User not found or invalid ID", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createUser(@RequestBody userPostDto: UserDto): ResponseEntity<UserDto> {
        val createdUser = userService.createUser(userPostDto)
        return ResponseEntity(createdUser, HttpStatus.CREATED)
    }
}
