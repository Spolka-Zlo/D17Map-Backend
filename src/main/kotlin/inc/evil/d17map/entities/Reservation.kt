package inc.evil.d17map.entities

import inc.evil.d17map.enums.ReservationType
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Entity
class Reservation(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    val id: UUID,
    var title: String,
    var date: LocalDate,
    var startTime: LocalTime,
    var endTime: LocalTime,

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    var classroom: Classroom,

    @Enumerated(EnumType.STRING)
    var type: ReservationType,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User
) {

}