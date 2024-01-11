package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.id.FileId
import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.springframework.stereotype.Component

@Component
class FileMapper(
    private val movementMapper: MovementMapper
) {
    fun toDomain(fileDocument: FileDocument, user: User): File {
        return File(
            FileId(fileDocument.id), user, fileDocument.filename,
            fileDocument.movements.map { movementMapper.toDomain(it) }
        )
    }
}