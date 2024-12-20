package inc.evil.d17map.security.authentication.jwt

import inc.evil.d17map.exceptions.JWTFilterException
import inc.evil.d17map.security.config.PublicRequestMatcher
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

private val kotlinLogger = KotlinLogging.logger {}

@Component
class JWTFilter(
    private val tokenProvider: TokenProvider,
    private val publicRequestMatcher: PublicRequestMatcher
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (publicRequestMatcher.matches(request)) {
            kotlinLogger.info { "Public endpoint accessed, skipping JWT filter." }
            filterChain.doFilter(request, response)
            return
        }

        try {
            extractToken(request)?.let { token ->
                SecurityContextHolder.getContext().authentication = tokenProvider.getAuthentication(token)
                kotlinLogger.info { "Authentication saved successfully." }
            } ?: kotlinLogger.info { "No token provided, skipping token verification." }

        } catch (ex: Exception) {
            val customException = when (ex) {
                is UnsupportedJwtException -> JWTFilterException("Unsupported JWT token: ${ex.message}")
                is JwtException -> JWTFilterException("Invalid JWT token: ${ex.message}")
                is IllegalArgumentException -> JWTFilterException("Token is invalid or malformed: ${ex.message}")
                else -> JWTFilterException("Unexpected JWT error: ${ex.message}")
            }

            kotlinLogger.error(ex) { "JWT Error: ${customException.message}" }
            throw customException
        }

        filterChain.doFilter(request, response)
    }

    fun extractToken(request: HttpServletRequest) =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)

}
