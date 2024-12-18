package inc.evil.d17map.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import inc.evil.d17map.exceptions.EnhancedErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN

        val errorResponse = EnhancedErrorResponse(
            errorCode = "ACCESS_DENIED",
            message = "Forbidden: ${accessDeniedException.message}",
            timestamp = LocalDateTime.now()
        )

        val jsonResponse = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(jsonResponse)
    }
}