package inc.evil.d17map.services

import inc.evil.d17map.exceptions.BuildingNotFoundException
import inc.evil.d17map.repositories.BuildingRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service


private val logger = KotlinLogging.logger {}


@Service
class BuildingService(
    private val buildingRepository: BuildingRepository
) {
    fun findBuilding(buildingName: String) =
        buildingRepository.findByName(buildingName) ?: throw BuildingNotFoundException(buildingName)
}