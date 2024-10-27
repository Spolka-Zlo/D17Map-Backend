package inc.evil.d17map.entities

import inc.evil.d17map.security.roles.Role
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

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val reservations: MutableSet<Reservation> = mutableSetOf()
) {
    constructor(
        id: UUID? = null,
        email: String,
        password: String,
        userType: MutableSet<Role>
    ) : this(id, email, password, userType, mutableSetOf())
}