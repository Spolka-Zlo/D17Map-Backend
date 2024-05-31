package inc.evil.persistance.repositories

import inc.evil.persistance.entities.Equipment
import java.util.UUID

interface EquipmentRepository : Repository<UUID, Equipment> {
    // TODO add type specific methods
}