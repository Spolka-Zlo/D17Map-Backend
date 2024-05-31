package inc.evil.persistance.repositories

import inc.evil.persistance.entities.User
import java.util.UUID

interface UserRepository : Repository<UUID, User> {
    // TODO add type specific methods
}