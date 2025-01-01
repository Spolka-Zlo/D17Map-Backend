package inc.evil.d17map.security.authorization


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Role?
    fun existsByName(name: String): Boolean

    @Query("""
        SELECT r FROM Role r
        JOIN r.classrooms c
        WHERE r.name IN :roleNames AND c.id = :classId
    """)
    fun findRolesWithClassroomAccess(roleNames: List<String>, classId: UUID): List<Role>
}