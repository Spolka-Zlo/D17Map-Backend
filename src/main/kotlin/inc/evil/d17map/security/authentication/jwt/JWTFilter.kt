package inc.evil.d17map.security.authentication.jwt


import io.github.oshai.kotlinlogging.KotlinLogging
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
    private val tokenProvider: TokenProvider
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            extractToken(request)
                ?.takeIf(tokenProvider::validateToken)
                ?.let {
                    kotlinLogger.info { "Token generated successfully. Clearing Security Context..." }
                    SecurityContextHolder.clearContext()
                    kotlinLogger.info { "Security Context cleared successfully. Saving new authentication..." }
                    SecurityContextHolder.getContext().authentication = tokenProvider.getAuthentication(it)
                    kotlinLogger.info { "Authentication saved successfully" }
                }
        } catch (ex: Exception) {
            kotlinLogger.error(ex) { "JWT Error: ${ex.message}" }
            return
        }


        filterChain.doFilter(request, response)
    }

    fun extractToken(request: HttpServletRequest) =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)


}
