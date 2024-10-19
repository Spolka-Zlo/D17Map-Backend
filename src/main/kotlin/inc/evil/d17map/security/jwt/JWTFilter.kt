package inc.evil.d17map.security.jwt


import inc.evil.d17map.security.user.BasicUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationContext
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JWTFilter(
    private val jwtService: JWTService,
    private val applicationContext: ApplicationContext
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request)
        val username = extractUsername(token)

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = loadUserDetails(username)
            setAuthentication(token, userDetails, request)
        }

        filterChain.doFilter(request, response)
    }

    fun extractToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else {
            null
        }
    }

    fun extractUsername(token: String?): String? {
        return token?.let { jwtService.extractUsername(it) }
    }

    fun loadUserDetails(username: String): UserDetails {
        return applicationContext.getBean(BasicUserDetailsService::class.java).loadUserByUsername(username)
    }

    fun setAuthentication(token: String?, userDetails: UserDetails, request: HttpServletRequest) {
        if (token != null && jwtService.validateToken(token, userDetails)) {
            val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
    }
}
