package inc.evil.d17map.entities

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Equipment(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var name: String,

    @ManyToMany(
        mappedBy = "equipments",
        fetch = FetchType.LAZY,
    )
    var classrooms: MutableSet<Classroom> = mutableSetOf()
) {

}