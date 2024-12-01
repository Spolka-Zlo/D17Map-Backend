package inc.evil.d17map.security.config

import inc.evil.d17map.security.authentication.jwt.JWTProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty


@ConfigurationProperties(prefix = "app.security")
data class SecurityProperties (
    @NestedConfigurationProperty
    val jwt: JWTProperties = JWTProperties()
)