package inc.evil.d17map.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    private val username: String,
    private val password: String
) : UserDetails {
    constructor(username: String) : this(username, "")

    private val authorities: MutableSet<GrantedAuthority> = mutableSetOf()


    fun setAuthorities(authorities: Collection<GrantedAuthority>) {
        this.authorities.clear()
        this.authorities.addAll(authorities)
    }

    fun addAuthority(authority: GrantedAuthority) {
        authorities.add(authority)
    }

    fun removeAuthority(authority: GrantedAuthority) {
        authorities.remove(authority)
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
