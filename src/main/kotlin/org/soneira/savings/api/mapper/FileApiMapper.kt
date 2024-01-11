package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.domain.entity.File
import org.springframework.stereotype.Component

@Component
class FileApiMapper(val movementApiMapper: MovementApiMapper) {
    fun toDto(file: File): FileDTO {
        return FileDTO(file.id.value, file.movements.map { movementApiMapper.toDto(it) })
    }
}