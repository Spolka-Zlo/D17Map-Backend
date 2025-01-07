package inc.evil.d17map.dtos.users

data class UserRequest( val username: String, val password: String? = null, val roles: List<String>? = null)