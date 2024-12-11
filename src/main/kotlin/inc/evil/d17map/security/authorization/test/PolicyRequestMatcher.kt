package inc.evil.d17map.security.authorization.test

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.util.matcher.RequestMatcher

class PolicyRequestMatcher : RequestMatcher {
    override fun matches(request: HttpServletRequest): Boolean {
        return request.requestURI.startsWith("/test")
    }
}