package inc.evil.d17map.controllers

import inc.evil.d17map.dtos.Model3dResponse
import inc.evil.d17map.services.Model3dService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/models")
@Tag(name = "Models")
class Model3dController(private val model3dService: Model3dService) {

    @Operation(
        summary = "Get model3D by building and floor",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved Model 3D for the specified building and floor."
            ),
            ApiResponse(responseCode = "400", description = "Query parameter 'building' and 'floor' must be specified."),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access. The user is not authenticated and needs to log in."
            )
        ]
    )
    @GetMapping
    fun getModelFilePath(
        @RequestParam building: String,
        @RequestParam floor: Int
    ): ResponseEntity<Model3dResponse> {
        val filePath = model3dService.getFilePathByBuildingAndFloor(building, floor)
        return ResponseEntity(filePath, HttpStatus.OK)
    }
}