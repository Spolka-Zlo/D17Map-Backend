package inc.evil.d17map.entities

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Photo(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    val id: UUID? = null,

    @Lob
    @Basic(fetch = FetchType.LAZY)
    var data: ByteArray
)