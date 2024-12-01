package inc.evil.d17map.security.authorization

import inc.evil.d17map.entities.User
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Role(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    val name: String,
    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<User> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "roles_privileges",
        joinColumns = [JoinColumn(
            name = "role_id", referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "privilege_id", referencedColumnName = "id"
        )]
    )
    val permissions: MutableSet<Permission> = mutableSetOf()
)