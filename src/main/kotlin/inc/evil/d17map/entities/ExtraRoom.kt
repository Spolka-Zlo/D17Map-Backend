package inc.evil.d17map.entities

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class ExtraRoom(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var name: String,
    var modelKey: String,
    var description: String,
    var type: String,

    @Column(name = "floor_id", nullable = false)
    val floorId: UUID
)
