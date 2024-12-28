package inc.evil.d17map.web.filters

import com.fasterxml.jackson.databind.ObjectMapper
import inc.evil.d17map.exceptions.ErrorCodes
import inc.evil.d17map.exceptions.ErrorResponse
import inc.evil.d17map.exceptions.JWTFilterException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


private val kotlinLogger = KotlinLogging.logger {}

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class GlobalExceptionHandlingFilter(private val objectMapper: ObjectMapper) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            handleException(response, ex)
        }
    }

    private fun handleException(
        response: HttpServletResponse,
        ex: Exception
    ) {
        kotlinLogger.error(ex) { "Exception caught in GlobalExceptionHandlingFilter: ${ex.message}" }

        val errorResponse = when (ex) {
            is JWTFilterException -> {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                ErrorResponse(
                    errorCode = ErrorCodes.UNAUTHORIZED_ERROR,
                    message = "${ex.message}"
                )
            }

            else -> {
                response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                ErrorResponse(
                    errorCode = ErrorCodes.UNKNOWN_ERROR,
                    message = "Internal Server Error: ${ex.message}"
                )
            }
        }

        val jsonResponse = objectMapper.writeValueAsString(errorResponse)
        response.contentType = "application/json"
        response.writer.use { it.write(jsonResponse) }
    }
}
