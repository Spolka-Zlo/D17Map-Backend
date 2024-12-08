package inc.evil.d17map.services

import inc.evil.d17map.dtos.ExtraRoomRequest
import inc.evil.d17map.dtos.ExtraRoomResponse
import inc.evil.d17map.entities.ExtraRoom
import inc.evil.d17map.mappers.toExtraRoomResponse
import inc.evil.d17map.repositories.ExtraRoomRepository
import org.springframework.stereotype.Service

@Service
class ExtraRoomService(
    private val extraRoomRepository: ExtraRoomRepository,
) {
    fun getExtraRooms(): List<ExtraRoomResponse> {
        val extraRooms = extraRoomRepository.findAll()
        return extraRooms.map {
            toExtraRoomResponse(it)
        }
    }

    fun createExtraRoom(extraRoomRequest: ExtraRoomRequest): ExtraRoomResponse {
        val extraRoom = ExtraRoom(
            name = extraRoomRequest.name,
            modelKey = extraRoomRequest.modelKey,
            description = extraRoomRequest.description,
            type = extraRoomRequest.type,
            floor = extraRoomRequest.floor
        )
        val savedExtraRoom = extraRoomRepository.save(extraRoom)
        return toExtraRoomResponse(savedExtraRoom)
    }
}