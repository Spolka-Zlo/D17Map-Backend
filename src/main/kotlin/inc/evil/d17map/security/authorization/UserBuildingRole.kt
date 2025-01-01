package inc.evil.d17map.security.authorization


import inc.evil.d17map.entities.Building
import inc.evil.d17map.entities.User
import jakarta.persistence.*

import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "users_buildings_roles")
class UserBuildingRole(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    val building: Building,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    val role: Role
)

