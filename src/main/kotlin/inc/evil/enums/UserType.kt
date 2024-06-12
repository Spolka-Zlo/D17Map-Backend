package inc.evil.enums

import kotlinx.serialization.Serializable

@Serializable
enum class UserType {
    ADMIN,
    STUDENT,
    TEACHER,
    STUDENT_COUNCIL_PRESIDENT,
    STUDENTS_CLUB_MEMBER
}