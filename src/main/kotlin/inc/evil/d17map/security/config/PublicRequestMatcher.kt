package inc.evil.d17map.security.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component

@Component
class PublicRequestMatcher : RequestMatcher {

    companion object {
        private val SWAGGER_ENDPOINTS = listOf(
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**"
        )

        private val OPEN_ENDPOINTS = listOf(
            "/buildings/{buildingName}/extra-rooms/**",
            "/buildings/{buildingName}/floors/**",
            "/buildings/{buildingName}/classrooms/**",
            "/buildings/{buildingName}/reservations/week",
            "/buildings/{buildingName}/reservations/events",
            "/reservations/types",
            "/equipments"
        )
    }

    private val publicMatchers: List<RequestMatcher> = buildPublicMatchers()

    override fun matches(request: HttpServletRequest): Boolean {
        println(request.requestURI)
        return publicMatchers.any { it.matches(request) }
    }

    private fun buildPublicMatchers(): List<RequestMatcher> {
        return SWAGGER_ENDPOINTS.map { AntPathRequestMatcher(it) } +
                OPEN_ENDPOINTS.map { AntPathRequestMatcher(it, HttpMethod.GET.name()) } +
                listOf(
                    AntPathRequestMatcher("/auth/**"),
                    AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name())
                )
    }
}
