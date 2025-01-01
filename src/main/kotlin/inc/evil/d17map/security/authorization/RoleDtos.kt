package inc.evil.d17map.security.authorization

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.DEDUCTION,
    include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes(
    JsonSubTypes.Type(value = RoleIncludedDto::class, name = "included"),
    JsonSubTypes.Type(value = RoleExcludedDto::class, name = "excluded")
)
sealed class RoleBaseDto(
    val id: UUID?,
    val name: String
)

class RoleIncludedDto(
    id: UUID?,
    name: String,
    val included: RoleDetails,
) : RoleBaseDto(id, name)

class RoleExcludedDto(
    id: UUID?,
    name: String,
    val excluded: RoleDetails,
) : RoleBaseDto(id, name)

class RoleDetails(
    val all: Boolean,
    val floors: List<String>?,
    val classrooms: List<UUID>?
)

