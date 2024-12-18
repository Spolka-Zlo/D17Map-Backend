package inc.evil.d17map.security.authorization.policy

import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.web.util.ContentCachingRequestWrapper
import java.util.*
import java.util.function.Supplier

class PolicyAuthorizationManager : AuthorizationManager<RequestAuthorizationContext> {
    @Deprecated("Deprecated in Java")
    override fun check(
        authentication: Supplier<Authentication>?,
        requestAuthorizationContext: RequestAuthorizationContext?
    ): AuthorizationDecision {
        val auth = authentication?.get() ?: throw IllegalStateException("Authentication not set")
        val request = requestAuthorizationContext?.request ?: throw IllegalStateException("Request is not set")

        val wrappedRequest = ContentCachingRequestWrapper(request)

        val method = HttpMethod.valueOf(wrappedRequest.method.uppercase(Locale.getDefault()))
        val url = wrappedRequest.requestURI
        val urlParameters = wrappedRequest.parameterMap.mapValues { (_, values) ->
            values.joinToString(",")
        }
        val requestBody = request.inputStream.use { it.readBytes() }

        // May be used later
        val contextParameters = emptyMap<String, String>()


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