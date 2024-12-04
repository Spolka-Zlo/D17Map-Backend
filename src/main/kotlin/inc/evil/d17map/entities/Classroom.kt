package inc.evil.d17map.entities

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Classroom(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var name: String,
    var description: String,
    var capacity: Int,
    val modelKey: String,
    val floorId: UUID,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "classrooms_equipments",
        joinColumns = [JoinColumn(name = "classroom_id")],
        inverseJoinColumns = [JoinColumn(name = "equipment_id")]
    )
    var equipments: MutableSet<Equipment> = mutableSetOf(),

    @OneToMany(
        mappedBy = "classroom",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true
    )
    val reservations: MutableSet<Reservation> = mutableSetOf()
)
