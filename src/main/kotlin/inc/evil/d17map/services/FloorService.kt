package inc.evil.d17map.services

import inc.evil.d17map.dtos.FloorRequest
import inc.evil.d17map.dtos.FloorResponse
import inc.evil.d17map.entities.Floor
import inc.evil.d17map.findOne
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
            building = buildingRepository.findOne(floorRequest.buildingId)!!
        )
        val savedFloor = floorRepository.save(floor)
        return FloorResponse(
            id = savedFloor.id!!,
            floorName = savedFloor.name,
            buildingName = savedFloor.building.name
        )
    }
}