package inc.evil.persistance.repositories.impl

import inc.evil.persistance.entities.Equipment
import inc.evil.persistance.repositories.EquipmentRepository
import java.util.*

class EquipmentRepositoryImpl : BaseRepository<UUID, Equipment>(Equipment), EquipmentRepository {
}