package inc.evil.mapper

import inc.evil.dto.UserSummaryDto
import inc.evil.entities.UserEntity
import inc.evil.enums.UserType

class UserMapper {
    fun toDTO(userEntity: UserEntity): UserSummaryDto {
        return UserSummaryDto(
            id = userEntity.id,
            email = userEntity.email
        )
    }

    fun toEntity(userSummaryDto: UserSummaryDto, password: String, role: UserType): UserEntity {
        return UserEntity(
            id = userSummaryDto.id,
            password = password,
            email = userSummaryDto.email,
            role = role
        )
    }
}
