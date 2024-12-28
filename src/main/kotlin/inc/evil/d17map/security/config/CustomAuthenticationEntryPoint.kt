package inc.evil.d17map.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import inc.evil.d17map.exceptions.ErrorCodes
import inc.evil.d17map.exceptions.ErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class CustomAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.error(authException) { "Exception caught in AuthenticationEntryPoint: ${authException.message}" }

        val errorResponse = ErrorResponse(
            errorCode = ErrorCodes.UNAUTHORIZED_ERROR,
            message = "${authException.message}",
        )

        val jsonResponse = objectMapper.writeValueAsString(errorResponse)
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.use { it.write(jsonResponse) }

    }
}