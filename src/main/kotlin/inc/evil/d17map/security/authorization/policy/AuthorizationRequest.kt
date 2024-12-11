package inc.evil.d17map.security.authorization.policy

import org.springframework.http.HttpMethod
import org.springframework.security.core.Authentication

data class AuthorizationRequest(
    val authentication: Authentication,
    val method: HttpMethod,
    val url: String,
    val urlParameters: Map<String, String>,
    val requestBody: String?,
    val contextParameters: Map<String, Any>
)