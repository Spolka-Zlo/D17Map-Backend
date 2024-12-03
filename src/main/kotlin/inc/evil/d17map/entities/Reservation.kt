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
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,
    var title: String,
    var description: String,

    @Temporal(TemporalType.DATE)
    var date: LocalDate,

    @Temporal(TemporalType.TIME)
    var startTime: LocalTime,

    @Temporal(TemporalType.TIME)
    var endTime: LocalTime,

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "classroom_id")
    var classroom: Classroom,

    @Enumerated(EnumType.STRING)
    var type: ReservationType,

    @ManyToOne(
        fetch = FetchType.LAZY,
    )
    @JoinColumn(name = "user_id")
    var user: User,

    var numberOfParticipants: Int
)