package inc.evil.dao

import inc.evil.entities.ClassroomDetailsEntity
import inc.evil.entities.ClassroomEntity
import inc.evil.tables.Classroom
import inc.evil.tables.ClassroomDetails
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.*

class ClassroomDAO : KoinComponent {
    fun getClassroomById(classroomId: UUID): ClassroomEntity? {
        return transaction {
            val classroomResultRow = Classroom.select { Classroom.id eq classroomId }
                .singleOrNull()
            val detailsResultRow = ClassroomDetails.select { ClassroomDetails.classroomId eq classroomId }
                .singleOrNull()

            if (classroomResultRow != null && detailsResultRow != null) {
                ClassroomEntity.fromResultRow(classroomResultRow, detailsResultRow)
            } else {
                null
            }
        }
    }

    fun getAllClassrooms(): List<ClassroomEntity> {
        return transaction {
            (Classroom innerJoin ClassroomDetails)
                .selectAll()
                .map { row ->
                    val classroomRow = row[Classroom.id]
                    val detailsRow = ClassroomDetails.select { ClassroomDetails.classroomId eq classroomRow.value }
                        .singleOrNull()
                    ClassroomEntity.fromResultRow(row, detailsRow!!)
                }
        }
    }

    fun createClassroom(classroom: ClassroomEntity) {
        transaction {
            val classroomId = ClassroomDetails.insertAndGetId {
                it[numberOfSeats] = classroom.details.numberOfSeats
                it[equipment] = classroom.details.equipment
            }

            Classroom.insert {
                it[id] = classroomId.value
                it[description] = classroom.description
            }
        }
    }


    fun updateClassroom(classroom: ClassroomEntity) {
        transaction {
            ClassroomDetails.update({ ClassroomDetails.classroomId eq classroom.id }) {
                it[numberOfSeats] = classroom.details.numberOfSeats
                it[equipment] = classroom.details.equipment
            }

            Classroom.update({ Classroom.id eq classroom.id }) {
                it[description] = classroom.description
            }
        }
    }

    fun deleteClassroom(classroomId: UUID) {
        transaction {
            ClassroomDetails.deleteWhere { ClassroomDetails.classroomId eq classroomId }
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

