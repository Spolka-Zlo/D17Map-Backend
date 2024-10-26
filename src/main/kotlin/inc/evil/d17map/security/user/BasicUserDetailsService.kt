package inc.evil.d17map.security.user

import inc.evil.d17map.entities.User
import inc.evil.d17map.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class BasicUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException(username)
        return UserPrincipal(user.id!!, user.email, user.password)
    }
}
