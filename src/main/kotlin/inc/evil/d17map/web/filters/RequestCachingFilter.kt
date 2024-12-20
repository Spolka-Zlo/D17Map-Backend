package inc.evil.d17map.web.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Order(Ordered.HIGHEST_PRECEDENCE + 1) // +1 because we want the GlobalExceptionHandlingFilter to be executed first
@Component
class RequestCachingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachedRequest = CachedBodyHttpServletRequest(request)
        filterChain.doFilter(cachedRequest, response)
    }
}