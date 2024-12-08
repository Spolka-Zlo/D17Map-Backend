package inc.evil.d17map.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler


class CustomAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN

        val errorDetails: MutableMap<String, Any> = HashMap()
        errorDetails["error"] = HttpServletResponse.SC_FORBIDDEN
        errorDetails["message"] = "Forbidden: " + accessDeniedException.message

        val jsonResponse = ObjectMapper().writeValueAsString(errorDetails)
        response.writer.write(jsonResponse)
    }
}