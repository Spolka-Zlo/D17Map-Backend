package inc.evil.d17map.security.jwt


import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

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
        extractToken(request)?.takeIf(tokenProvider::validateToken)?.let { token ->
            SecurityContextHolder.getContext().authentication = tokenProvider.getAuthentication(token)
        }

        filterChain.doFilter(request, response)
    }

    fun extractToken(request: HttpServletRequest) =
        request.getHeader("Authorization")?.takeIf { it.startsWith("Bearer ") }?.substring(7)


}
