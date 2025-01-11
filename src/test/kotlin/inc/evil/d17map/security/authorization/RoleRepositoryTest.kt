package inc.evil.d17map.security.authorization

import inc.evil.d17map.helpers.*
import inc.evil.d17map.repositories.BuildingRepository
import inc.evil.d17map.repositories.ClassroomRepository
import inc.evil.d17map.repositories.FloorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@DataJpaTest
class RoleRepositoryTest {
    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Autowired
    private lateinit var buildingRepository: BuildingRepository

    @Autowired
    private lateinit var floorRepository: FloorRepository

    @Autowired
    private lateinit var classroomRepository: ClassroomRepository

    @Autowired
    private lateinit var permissionRepository: PermissionRepository


    @Test
    fun `should save role with permissions`() {
        // given
        val givenBuilding = givenBuilding()
        val givenPermission = givenPermission(true)
        val givenRole = givenRole(givenBuilding = givenBuilding, givenPermission = givenPermission)

        // when
        buildingRepository.save(givenBuilding)
        permissionRepository.save(givenPermission)
        val savedRole = roleRepository.save(givenRole)

        // then
        assertNotNull(savedRole)
        assertNotNull(savedRole.id)
        assertEquals(givenBuilding, savedRole.building)
        assertEquals(givenPermission, savedRole.permission)
    }

    @Test
    fun `should save role with included floors`() {
        // given
        val givenBuilding = givenBuilding()
        val givenFloors = givenFloors(givenBuilding).toMutableSet()
        val givenPermission = givenPermission(givenAllow = true, givenTakeAll = false, givenFloors = givenFloors)
        val givenRole = givenRole(givenBuilding = givenBuilding, givenPermission = givenPermission)

        // when
        buildingRepository.save(givenBuilding)
        floorRepository.saveAll(givenFloors)
        permissionRepository.save(givenPermission)
        val savedRole = roleRepository.save(givenRole)

        // then
        assertNotNull(savedRole)
        assertNotNull(savedRole.id)
        assertEquals(givenPermission, savedRole.permission)
        assertEquals(givenFloors, savedRole.permission.floors)
    }


    @Test
    fun `should save role with excluded floors and classroom`() {
        // given
        val givenBuilding = givenBuilding()
        val givenFloors = givenFloors(givenBuilding)
        val givenClassrooms = givenClassrooms(givenFloors)
        val givenPermission =
            givenPermission(
                givenAllow = false,
                givenFloors = givenFloors.toMutableSet(),
                givenClassrooms = givenClassrooms.toMutableSet()
            )
        val givenRole = givenRole(givenBuilding = givenBuilding, givenPermission = givenPermission)

        // when
        buildingRepository.save(givenBuilding)
        floorRepository.saveAll(givenFloors)
        classroomRepository.saveAll(givenClassrooms)
        permissionRepository.save(givenPermission)
        val savedRole = roleRepository.save(givenRole)

        // then
        assertNotNull(savedRole)
        assertNotNull(savedRole.id)
        assertEquals(givenPermission, savedRole.permission)
        assertEquals(givenFloors, savedRole.permission.floors.toList())
        assertEquals(givenClassrooms, savedRole.permission.classrooms.toList())
    }


    @Test
    fun `should find all roles for the building with permissions included`() {
        // given
        val givenBuilding = givenBuilding()
        val givenFloors = givenFloors(givenBuilding)


        val givenPermission =
            givenPermission(givenAllow = true, givenTakeAll = false, givenFloors = givenFloors.toMutableSet())
        val givenRole = givenRole(givenBuilding = givenBuilding, givenPermission = givenPermission)

        // when
        buildingRepository.save(givenBuilding)
        floorRepository.saveAll(givenFloors)
        permissionRepository.save(givenPermission)
        roleRepository.save(givenRole)
        val foundRoles = roleRepository.findAllByBuildingWithPermissions(givenBuilding)


        assertEquals(1, foundRoles.size)
        assertEquals(givenPermission, foundRoles.first().permission)

    }


}