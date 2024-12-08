package inc.evil.d17map.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint


class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        val errorDetails: MutableMap<String, Any> = HashMap()
        errorDetails["error"] = HttpServletResponse.SC_UNAUTHORIZED
        errorDetails["message"] = "Unauthorized: " + authException.message

        val jsonResponse = ObjectMapper().writeValueAsString(errorDetails)
        response.writer.write(jsonResponse)
    }
}