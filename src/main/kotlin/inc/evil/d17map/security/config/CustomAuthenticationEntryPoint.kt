package inc.evil.d17map.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import inc.evil.d17map.exceptions.EnhancedErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.time.LocalDateTime


class CustomAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val errorResponse = EnhancedErrorResponse(
            errorCode = "UNAUTHORIZED",
            message = "Unauthorized: ${authException.message}",
            timestamp = LocalDateTime.now()
        )

        val jsonResponse = objectMapper.writeValueAsString(errorResponse)
        response.writer.write(jsonResponse)
    }
}