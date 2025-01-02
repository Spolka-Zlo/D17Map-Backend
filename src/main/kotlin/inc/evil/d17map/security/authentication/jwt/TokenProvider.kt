package inc.evil.d17map.security.authentication.jwt


import inc.evil.d17map.security.UserPrincipal
import inc.evil.d17map.security.authentication.AuthService
import inc.evil.d17map.security.authorization.Role
import inc.evil.d17map.security.authorization.UserBuildingRoleRepository
import inc.evil.d17map.security.config.SecurityProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.security.NoSuchAlgorithmException
import java.time.ZonedDateTime
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


@Service
class TokenProvider(
    private val securityProperties: SecurityProperties,
    private val userBuildingRoleRepository: UserBuildingRoleRepository
) {
    private val secretKey: SecretKey = try {
        val keyGenerator = KeyGenerator.getInstance(securityProperties.jwt.keyGenerator)
        keyGenerator.generateKey()
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }

    fun getRoles(username: String, buildingName: String) =
        userBuildingRoleRepository.findAllByUserEmailAndBuildingName(username, buildingName)
            .map { it.role }
            .takeIf { it.isNotEmpty() }
            ?: listOf(Role(name = AuthService.DEFAULT_ROLE))


    fun generateToken(authentication: Authentication, buildingName: String): String {
        val principal = authentication.principal as UserPrincipal
        val roles = getRoles(principal.username, buildingName)

        val authorities = roles.joinToString(",") { it.name }

        val now = ZonedDateTime.now()
        val expirationDateTime = now.plusSeconds(securityProperties.jwt.ttlInSeconds)

        val issueDate = Date.from(now.toInstant())
        val expirationDate = Date.from(expirationDateTime.toInstant())

        return Jwts.builder()
            .claims()
            .subject(principal.username)
            .issuedAt(issueDate)
            .expiration(expirationDate)
            .add("AUTHORITIES", authorities)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun extractClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload


    fun getAuthentication(token: String): Authentication {
        val claims: Claims = extractClaims(token)

        val authorities: Collection<GrantedAuthority> =
            claims["AUTHORITIES"].toString().split(",")
                .map { SimpleGrantedAuthority(it) }

        val userPrincipal = UserPrincipal(
            username = claims.subject
        )
        userPrincipal.authorities = authorities
        return UsernamePasswordAuthenticationToken.authenticated(userPrincipal, null, authorities)
    }
}