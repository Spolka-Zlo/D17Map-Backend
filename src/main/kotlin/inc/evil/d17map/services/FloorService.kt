package inc.evil.d17map.services

import inc.evil.d17map.dtos.FloorRequest
import inc.evil.d17map.dtos.FloorResponse
import inc.evil.d17map.entities.Floor
import inc.evil.d17map.repositories.FloorRepository
import org.springframework.stereotype.Service


@Service
class FloorService(
    private val floorRepository: FloorRepository
) {

    fun getFloors(): List<Floor> = floorRepository.findAll()

    fun createFloor(floorRequest: FloorRequest): FloorResponse {
        val floor = Floor(
            name = floorRequest.name,
            buildingId = floorRequest.buildingId
        )
        val savedFloor = floorRepository.save(floor)
        return FloorResponse(
            id = savedFloor.id.toString(),
            name = savedFloor.name,
            buildingId = savedFloor.buildingId
        )
    }
}