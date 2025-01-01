package inc.evil.d17map.security.authentication

import inc.evil.d17map.repositories.UserRepository
import inc.evil.d17map.security.UserPrincipal
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
                it.password
            )
        } ?: throw UsernameNotFoundException(username)
}
