package inc.evil.entities

import inc.evil.tables.Classroom
import inc.evil.tables.ClassroomDetails
import org.jetbrains.exposed.sql.ResultRow
import java.util.UUID

data class ClassroomEntity(
    val id: UUID,
    val description: String,
    val details: ClassroomDetailsEntity
) {
    companion object {
        fun fromResultRow(classroomRow: ResultRow, detailsRow: ResultRow): ClassroomEntity {
            return ClassroomEntity(
                id = classroomRow[Classroom.id].value,
                description = classroomRow[Classroom.description],
                details = ClassroomDetailsEntity.fromResultRow(detailsRow)
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
                classroomId = row[ClassroomDetails.classroomId].value,
                numberOfSeats = row[ClassroomDetails.numberOfSeats],
                equipment = row[ClassroomDetails.equipment]
            )
        }
    }
}
