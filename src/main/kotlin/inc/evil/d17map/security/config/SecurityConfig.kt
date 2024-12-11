package inc.evil.d17map.security.config

import inc.evil.d17map.security.authentication.jwt.JWTFilter
import inc.evil.d17map.security.authorization.policy.PolicyAuthorizationManager
import inc.evil.d17map.security.authorization.test.PolicyRequestMatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtFilter: JWTFilter
) {

    private companion object {
        private val SWAGGER_ENDPOINTS = arrayOf(
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
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .exceptionHandling {
                it
                    .accessDeniedHandler(CustomAccessDeniedHandler())
                    .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/auth/**",
                        *SWAGGER_ENDPOINTS
                    ).permitAll()
                    .requestMatchers(
                        HttpMethod.OPTIONS, "/**"
                    ).permitAll()
                    .requestMatchers(PolicyRequestMatcher()).access(PolicyAuthorizationManager())
                    .anyRequest().permitAll()        }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterAfter(jwtFilter, LogoutFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userDetailsService)
        return provider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}
