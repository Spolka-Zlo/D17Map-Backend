package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.User
import inc.evil.persistance.repositories.UserRepository
import java.util.UUID

class UserRepositoryImpl : BaseRepository<User, UUID>(), UserRepository {
}