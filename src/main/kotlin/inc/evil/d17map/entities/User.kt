package inc.evil.d17map.entities

import inc.evil.d17map.enums.Role
import jakarta.persistence.*

import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "app_user")
class User(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var email: String,
    var password: String,

    @Enumerated(EnumType.STRING)
    var userType: Role,

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val reservations: MutableSet<Reservation> = mutableSetOf()
)