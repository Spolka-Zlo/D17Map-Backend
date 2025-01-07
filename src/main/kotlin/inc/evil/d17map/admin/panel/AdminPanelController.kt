package inc.evil.d17map.admin.panel

import inc.evil.d17map.dtos.users.UserRequest
import inc.evil.d17map.dtos.users.UserResponse
import inc.evil.d17map.entities.User
import inc.evil.d17map.services.UserService


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Admin")
class AdminPanelController(
    private val userService: UserService
) {

    companion object {
        private const val BUILDINGS_PATH = "/buildings/{buildingName}"
    }

    @Operation(
        summary = "Create a new user (for admin only)",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully created a user."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PostMapping("$BUILDINGS_PATH/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @PathVariable buildingName: String,
        @RequestBody userRequest: UserRequest
    ): ResponseEntity<UserResponse> {

        val user = userService.createUser(userRequest, buildingName)
        return ResponseEntity.ok(user)
    }

    @Operation(
        summary = "Update a user (for admin only)",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully updated a user."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @PutMapping("$BUILDINGS_PATH/users")
    fun updateUser(
        @PathVariable buildingName: String,
        @RequestBody userRequest: UserRequest
    ): ResponseEntity<UserResponse> {

        val user = userService.updateUser(userRequest, buildingName)
        return ResponseEntity(user, HttpStatus.OK)
    }

}
