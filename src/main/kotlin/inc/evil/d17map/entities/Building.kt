package inc.evil.d17map.entities

import inc.evil.d17map.security.authorization.UserBuildingRole
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Building(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var name: String
) {
    @OneToMany(mappedBy = "building", cascade = [CascadeType.ALL], orphanRemoval = true)
    val usersBuildingsRoles: MutableSet<UserBuildingRole> = mutableSetOf()
}