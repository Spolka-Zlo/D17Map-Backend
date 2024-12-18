package inc.evil.d17map.security.authentication

import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.UserPrincipal
import inc.evil.d17map.security.authorization.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class BasicUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String) =
        userRepository.findByEmail(username)?.let {
            UserPrincipal(
                it.email,
                it.password,
                it.roles.toGrantedAuthorities()
            )
        } ?: throw UsernameNotFoundException(username)
}

private fun Collection<Role>.toGrantedAuthorities(): Collection<GrantedAuthority> =
    flatMap { it.permissions.map { privilege -> privilege.name } + it.name }
        .map { SimpleGrantedAuthority(it) }



