package org.soneira.savings.api.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.domain.entity.File

@Mapper(uses = [MovementMapper::class])
interface FileMapper {
    @Mapping(source = "id.value", target = "fileId")
    fun toDto(file: File): FileDTO
}