package inc.evil.d17map.security.jwt


data class JWTProperties (
    val ttlInSeconds: Long = 3600,
    val keyGenerator: String = "HmacSHA256"
)