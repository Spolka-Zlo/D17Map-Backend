package inc.evil.d17map.entities

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Floor (
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var name: String,

    @Column(name = "building_id", nullable = false)
    val buildingId: UUID,

    @OneToMany(
        mappedBy = "floorId",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true
    )
    val classrooms: MutableSet<Classroom> = mutableSetOf(),

    @OneToMany(
        mappedBy = "floorId",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true
    )
    val extraRooms: MutableSet<ExtraRoom> = mutableSetOf()
)