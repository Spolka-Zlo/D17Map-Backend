package inc.evil.d17map.services

import inc.evil.d17map.dtos.Model3dResponse
import inc.evil.d17map.mappers.toModel3dResponse
import inc.evil.d17map.repositories.Model3dRepository
import org.springframework.stereotype.Service

@Service
class Model3dService(private val model3dRepository: Model3dRepository) {

    fun getFilePathByBuildingAndFloor(building: String, floor: Int): Model3dResponse? {
        val model = model3dRepository.findByBuildingAndFloor(building, floor)
        return model.map { toModel3dResponse(it.filePath) }.orElse(null)
    }
}
