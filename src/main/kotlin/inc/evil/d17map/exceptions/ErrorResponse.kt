package inc.evil.d17map.exceptions

import java.time.LocalDateTime

data class ErrorResponse(
    val errorCode: ErrorCodes,
    val message: String?,
    val timestamp: LocalDateTime = LocalDateTime.now()
)