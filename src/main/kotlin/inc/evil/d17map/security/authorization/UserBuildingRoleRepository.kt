package inc.evil.d17map.security.authorization

import inc.evil.d17map.entities.Building
import inc.evil.d17map.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserBuildingRoleRepository : JpaRepository<UserBuildingRole, UUID> {
    fun existsByUserAndBuildingAndRole(user: User, building: Building, role: Role): Boolean
    fun findByUserAndBuildingAndRole(user: User, building: Building, role: Role): UserBuildingRole?

    fun findAllByUserEmailAndBuildingName(email: String, buildingName: String): List<UserBuildingRole>

    fun findAllByUserAndBuilding(user: User, building: Building): List<UserBuildingRole>

    fun findAllByBuilding_Name(buildingName: String): List<UserBuildingRole>
}