package inc.evil.d17map.services

import inc.evil.d17map.dtos.FloorRequest
import inc.evil.d17map.dtos.FloorResponse
import inc.evil.d17map.entities.Floor
import inc.evil.d17map.exceptions.BuildingNotFoundException
import inc.evil.d17map.exceptions.InvalidReservationDataException
import inc.evil.d17map.findOne
import inc.evil.d17map.repositories.BuildingRepository
import inc.evil.d17map.repositories.FloorRepository
import org.springframework.stereotype.Service


@Service
class FloorService(
    private val floorRepository: FloorRepository,
    private val buildingRepository: BuildingRepository
) {

    fun getFloors(buildingName: String): List<Floor> = floorRepository.findByBuildingName(buildingName)

    fun createFloor(floorRequest: FloorRequest, buildingName: String): FloorResponse {
        val building = buildingRepository.findOne(floorRequest.buildingId)
            ?: throw BuildingNotFoundException(buildingName)

        if (buildingName != building.name) {
            throw InvalidReservationDataException(
                "The building name '$buildingName' does not match the " +
                        "building name from  create floor request '${building.name}'."
            )
        }

        val floor = Floor(
            name = floorRequest.floorName,
            building = building
        )

        val savedFloor = floorRepository.save(floor)
        return FloorResponse(
            id = savedFloor.id!!,
            floorName = savedFloor.name,
        )
    }
}