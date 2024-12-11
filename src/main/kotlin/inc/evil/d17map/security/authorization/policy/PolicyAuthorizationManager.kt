package inc.evil.d17map.security.authorization.policy

import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import java.util.*
import java.util.function.Supplier

class PolicyAuthorizationManager : AuthorizationManager<RequestAuthorizationContext> {
    @Deprecated("Deprecated in Java")
    override fun check(
        authentication: Supplier<Authentication>?,
        requestAuthorizationContext: RequestAuthorizationContext?
    ): AuthorizationDecision? {

        val auth = authentication?.get() ?: return null
        val request = requestAuthorizationContext?.request ?: return null

        val method = HttpMethod.valueOf(request.method.uppercase(Locale.getDefault()))
        val url = request.requestURI
        val urlParameters = request.parameterMap.mapValues { (_, values) ->
            values.joinToString(",")
        }

        // May be used later
        val contextParameters = emptyMap<String, String>()

        val requestBody = request.inputStream
            ?.bufferedReader()
            ?.use { it.readText() }
            ?.takeIf { it.isNotBlank() }

        val authorizationRequest = AuthorizationRequest(
            authentication = auth,
            method = method,
            url = url,
            urlParameters = urlParameters,
            requestBody = requestBody,
            contextParameters = contextParameters
        )

        println(authorizationRequest)
        return AuthorizationDecision(true)
    }

}