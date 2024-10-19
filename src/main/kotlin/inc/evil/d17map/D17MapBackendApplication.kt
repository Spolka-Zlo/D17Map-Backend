package inc.evil.d17map

import inc.evil.d17map.security.config.SecurityProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [SecurityProperties::class])
class D17MapBackendApplication

fun main(args: Array<String>) {
    runApplication<D17MapBackendApplication>(*args)
}
