package inc.evil.d17map.entities

import inc.evil.d17map.enums.Role
import jakarta.persistence.*

import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class User(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    val id: UUID,
    var email: String,
    var password: String,

    // change to entity if roles should be editable
    @Enumerated(EnumType.STRING)
    var userType: Role,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reservations: MutableList<Reservation> = ArrayList()
)