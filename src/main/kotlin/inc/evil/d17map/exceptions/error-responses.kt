package inc.evil.d17map.exceptions

import java.time.LocalDateTime

data class ErrorResponse(val message: String)
data class EnhancedErrorResponse(val errorCode: String, val message: String, val timestamp: LocalDateTime)
