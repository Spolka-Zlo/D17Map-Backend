package inc.evil.d17map.services

import inc.evil.d17map.dtos.ExtraRoomRequest
import inc.evil.d17map.dtos.ExtraRoomResponse
import inc.evil.d17map.entities.Building
import inc.evil.d17map.entities.ExtraRoom
import inc.evil.d17map.entities.Floor
import inc.evil.d17map.exceptions.InvalidExtraRoomDataException
import inc.evil.d17map.mappers.toExtraRoomResponse
import inc.evil.d17map.repositories.ExtraRoomRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExtraRoomService(
    private val extraRoomRepository: ExtraRoomRepository,
) {
    fun getExtraRooms(buildingName: String, floorName: String): List<ExtraRoomResponse> {
        val extraRooms = extraRoomRepository.findByBuildingAndFloor(buildingName, floorName)
        return extraRooms.map { toExtraRoomResponse(it) }
    }

    fun getExtraRoomsByBuilding(buildingName: String): List<ExtraRoomResponse> {
        val extraRooms = extraRoomRepository.findByBuilding(buildingName)
        return extraRooms.map { toExtraRoomResponse(it) }
    }

    fun createExtraRoom(buildingName: String, floorName: String, extraRoomRequest: ExtraRoomRequest): ExtraRoomResponse {
        val extraRoom = ExtraRoom(
            name = extraRoomRequest.name,
            modelKey = extraRoomRequest.modelKey,
            description = extraRoomRequest.description,
            type = extraRoomRequest.type,
            floor = Floor(
                name = floorName,
                building = Building(name = buildingName)
            )
        )
        val savedExtraRoom = extraRoomRepository.save(extraRoom)
        return toExtraRoomResponse(savedExtraRoom)
    }

    fun deleteExtraRoom(buildingName: String, id: UUID) {
        val extraRoom = extraRoomRepository.findByIdAndBuilding(id, buildingName)
            ?: throw InvalidExtraRoomDataException("Extra room with ID $id not found in building '$buildingName'.")
        extraRoomRepository.delete(extraRoom)
    }
}
