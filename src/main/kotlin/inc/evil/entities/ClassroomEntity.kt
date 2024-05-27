package inc.evil.entities

import inc.evil.tables.Classroom
import inc.evil.tables.ClassroomDetails
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

data class ClassroomEntity(
    val id: UUID,
    val description: String,
    val details: String
) {
    companion object {
        fun fromResultRow(row: ResultRow): ClassroomEntity {
            return ClassroomEntity(
                id = row[Classroom.id].value,
                description = row[Classroom.description],
                details = row[Classroom.details]
            )
        }
    }
}

data class ClassroomDetailsEntity(
    val classroomId: UUID,
    val numberOfSeats: Int,
    val equipment: String
) {
    companion object {
        fun fromResultRow(row: ResultRow): ClassroomDetailsEntity {
            return ClassroomDetailsEntity(
                classroomId = row[ClassroomDetails.classroomId].value,  // Extract UUID from EntityID
                numberOfSeats = row[ClassroomDetails.numberOfSeats],
                equipment = row[ClassroomDetails.equipment]
            )
        }
    }
}
