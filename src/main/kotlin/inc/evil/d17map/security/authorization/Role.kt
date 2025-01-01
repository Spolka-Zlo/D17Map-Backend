package inc.evil.d17map.security.authorization

import inc.evil.d17map.entities.Classroom
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Role(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    @ManyToMany
    @JoinTable(
        name = "roles_classrooms",
        joinColumns = [JoinColumn(
            name = "role_id", referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "classroom_id", referencedColumnName = "id"
        )]
    )
    val classrooms: MutableSet<Classroom> = mutableSetOf()
) {
    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], orphanRemoval = true)
    val usersBuildingsRoles: MutableSet<UserBuildingRole> = mutableSetOf()
}