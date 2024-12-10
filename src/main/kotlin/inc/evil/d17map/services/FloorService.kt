package inc.evil.d17map.services

import inc.evil.d17map.dtos.FloorRequest
import inc.evil.d17map.dtos.FloorResponse
import inc.evil.d17map.entities.Floor
import inc.evil.d17map.repositories.BuildingRepository
import inc.evil.d17map.repositories.FloorRepository
import org.springframework.stereotype.Service


@Service
class FloorService(
    private val floorRepository: FloorRepository,
    private val buildingRepository: BuildingRepository
) {

    fun getFloors(): List<Floor> = floorRepository.findAll()

    fun createFloor(floorRequest: FloorRequest): FloorResponse {
        val floor = Floor(
            name = floorRequest.floorName,
            building = buildingRepository.findById(floorRequest.buildingId).get()
        )
        val savedFloor = floorRepository.save(floor)
        return FloorResponse(
            id = savedFloor.id!!,
            name = savedFloor.name,
            buildingName = savedFloor.building.name
        )
    }
}