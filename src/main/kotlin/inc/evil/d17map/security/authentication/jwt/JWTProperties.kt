package inc.evil.d17map.security.authentication.jwt


data class JWTProperties (
    val ttlInSeconds: Long = 3600,
    val keyGenerator: String = "HmacSHA256"
)