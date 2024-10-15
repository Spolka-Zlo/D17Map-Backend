package inc.evil.d17map.security.jwt


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@Service
class JWTService {

    private val sec: SecretKey = try {
        val keyGenerator = KeyGenerator.getInstance("HmacSHA256")
        keyGenerator.generateKey()
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }

    fun generateToken(username: String): String {
        val claims: Map<String, Any> = HashMap()
        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24-hour expiration
            .and()
            .signWith(sec)
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(sec)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
