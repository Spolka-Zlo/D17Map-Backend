package inc.evil.d17map.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    info = Info(
        contact = Contact(
            name = "Maya",
            email = "mammaye4@gmail.com",
            url = "https://github.com/Spolka-Zlo"
        ),
        description = "Backend API documentation for our beloved front-end devs :)",
        title = "D17Map API documentation",
        version = "1.0",
    ),
    servers = [
        Server(
            description = "Local Development Server",
            url = "http://localhost:8080"
        )
    ],
    security = [SecurityRequirement(name = "bearerAuthorization")]
)
@SecurityScheme(
    name = "bearerAuthorization",
    description = "JWT as Bearer Authorization",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER,
)
class OpenApiConfig {
}