package inc.evil.d17map.entities

import inc.evil.d17map.security.authorization.UserBuildingRole
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var email: String,
    var password: String,

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val reservations: MutableSet<Reservation> = mutableSetOf()
) {
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val usersBuildingsRoles: MutableSet<UserBuildingRole> = mutableSetOf()

    constructor(
        id: UUID? = null,
        email: String,
        password: String,
    ) : this(id, email, password, mutableSetOf())
}