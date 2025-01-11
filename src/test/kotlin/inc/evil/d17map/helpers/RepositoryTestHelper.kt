package inc.evil.d17map.helpers

import inc.evil.d17map.entities.Building
import inc.evil.d17map.entities.Classroom
import inc.evil.d17map.entities.Floor
import inc.evil.d17map.security.authorization.Permission
import inc.evil.d17map.security.authorization.Role


fun givenPermission(
    givenAllow: Boolean,
    givenTakeAll: Boolean = true,
    givenFloors: MutableSet<Floor> = mutableSetOf(),
    givenClassrooms: MutableSet<Classroom> = mutableSetOf(),
) = Permission(
    allow = givenAllow,
    takeAll = givenTakeAll,
    floors = givenFloors,
    classrooms = givenClassrooms
)

fun givenBuilding(name: String = "givenName") =
    Building(name = name)


fun givenBuildings() = listOf(
    Building(name = "givenName1"),
    Building(name = "givenName2"),
    Building(name = "givenName3")
)


fun givenFloor(givenBuilding: Building) = Floor(
    name = "1",
    building = givenBuilding,
)

fun givenFloors(givenBuilding: Building) = listOf(
    Floor(
        name = "1",
        building = givenBuilding
    ),
    Floor(
        name = "2",
        building = givenBuilding
    )
)

fun givenClassroom(givenFloor: Floor) = Classroom(
    name = "givenName",
    floor = givenFloor,
    description = "givenDescription",
    capacity = 10,
    modelKey = "givenModelKey"
)

fun givenClassrooms(givenFloors: List<Floor>) = listOf(
    Classroom(
        name = "givenName",
        floor = givenFloors[0],
        description = "givenDescription",
        capacity = 10,
        modelKey = "givenModelKey"

    ),
    Classroom(
        name = "givenName2",
        floor = givenFloors[0],
        description = "givenDescription2",
        capacity = 20,
        modelKey = "givenModelKey2"
    ),
    Classroom(
        name = "givenName3",
        floor = givenFloors[1],
        description = "givenDescription3",
        capacity = 12,
        modelKey = "givenModelKey3"
    ),
    Classroom(
        name = "givenName4",
        floor = givenFloors[1],
        description = "givenDescription4",
        capacity = 20,
        modelKey = "givenModelKey4"
    )
)


fun givenRole(name: String = "givenName", givenBuilding: Building, givenPermission: Permission) = Role(
    name = name,
    building = givenBuilding,
    permission = givenPermission,
)



