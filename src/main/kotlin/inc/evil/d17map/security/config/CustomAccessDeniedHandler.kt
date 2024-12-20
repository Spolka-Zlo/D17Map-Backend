package inc.evil.d17map.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import inc.evil.d17map.exceptions.ErrorCodes
import inc.evil.d17map.exceptions.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class CustomAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        logger.error(accessDeniedException) { "Exception caught in AccessDeniedHandler: ${accessDeniedException.message}" }


        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN

        val errorResponse = ErrorResponse(
            errorCode = ErrorCodes.ACCESS_DENIED_ERROR,
            message = "Forbidden: ${accessDeniedException.message}",
        )

        val jsonResponse = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(jsonResponse)
    }
}