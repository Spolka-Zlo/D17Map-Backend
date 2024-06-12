package inc.evil.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ReservationType {
    CLASS,
    EXAM,
    TEST,
    CONSULTATIONS,
    CONFERENCE,
    STUDENTS_CLUB_MEETING,
    EVENT
}