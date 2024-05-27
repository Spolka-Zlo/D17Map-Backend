package inc.evil.dao

import inc.evil.entities.ClassroomDetailsEntity
import inc.evil.entities.ClassroomEntity
import inc.evil.tables.Classroom
import inc.evil.tables.ClassroomDetails
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.*

class ClassroomDAO : KoinComponent {
    fun getClassroomById(classroomId: UUID): ClassroomEntity? {
        return transaction {
            Classroom.select { Classroom.id eq classroomId }
                .map { ClassroomEntity.fromResultRow(it) }
                .singleOrNull()
        }
    }

    fun getAllClassrooms(): List<ClassroomEntity> {
        return transaction {
            Classroom.selectAll()
                .map { ClassroomEntity.fromResultRow(it) }
        }
    }

    fun createClassroom(classroom: ClassroomEntity) {
        transaction {
            Classroom.insert {
                it[id] = EntityID(classroom.id, Classroom)
                it[description] = classroom.description
                it[details] = classroom.details
            }
        }
    }

    fun updateClassroom(classroom: ClassroomEntity) {
        transaction {
            Classroom.update({ Classroom.id eq classroom.id }) {
                it[description] = classroom.description
                it[details] = classroom.details
            }
        }
    }

    fun deleteClassroom(classroomId: UUID) {
        transaction {
            Classroom.deleteWhere { Classroom.id eq classroomId }
        }
    }

    fun getClassroomDetails(classroomId: UUID): ClassroomDetailsEntity? {
        return transaction {
            ClassroomDetails.select { ClassroomDetails.classroomId eq classroomId }
                .map { ClassroomDetailsEntity.fromResultRow(it) }
                .singleOrNull()
        }
    }
}
