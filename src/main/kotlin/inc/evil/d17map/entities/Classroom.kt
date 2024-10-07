package inc.evil.d17map.entities

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "classrooms")
class Classroom(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    var id: UUID,
    var name: String,
    var description: String,
    var capacity: Int,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "classrooms_equipments",
        joinColumns = [JoinColumn(name = "classroom_id")],
        inverseJoinColumns = [JoinColumn(name = "equipment_id")]
    )
    var equipments: MutableSet<Equipment> = mutableSetOf(),

    @OneToMany(mappedBy = "classroom", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reservations: MutableSet<Classroom> = mutableSetOf()
) {
}