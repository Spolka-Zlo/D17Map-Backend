package inc.evil.d17map.security.authorization

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Permission(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,

    val name: String,

    @ManyToMany
    val roles: MutableSet<Role> = mutableSetOf()
)