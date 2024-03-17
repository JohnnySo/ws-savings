package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.vo.id.FileId
import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.springframework.stereotype.Component

@Component
class FileMapper(
    private val movementMapper: MovementMapper
) {
    fun asFile(fileDocument: FileDocument, user: User): File {
        return File(
            FileId(fileDocument.id), user, fileDocument.filename,
            fileDocument.movements.map { movementMapper.asMovement(it) }
        )
    }
}