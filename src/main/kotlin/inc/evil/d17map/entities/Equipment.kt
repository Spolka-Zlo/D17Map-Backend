package inc.evil.d17map.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Equipment(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    val id: UUID,
    var name: String,

    @ManyToMany(mappedBy = "equipments")
    var classrooms: MutableSet<Classroom> = mutableSetOf()
) {

}