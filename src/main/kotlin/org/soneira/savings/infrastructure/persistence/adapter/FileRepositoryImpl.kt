package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.PreMovement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.exception.ResourceNotFoundException
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.soneira.savings.domain.vo.id.FileId
import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.soneira.savings.infrastructure.persistence.mongo.mapper.MovementMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.FileMongoRepository

class FileRepositoryImpl(private val fileRepository: FileMongoRepository, private val movementMapper: MovementMapper) :
    FileRepository {
    override fun save(user: User, filename: String, movements: List<PreMovement>): File {
        val fileDocument =
            fileRepository.save(FileDocument(user.id.value, filename, movementMapper.preMovementsToDocument(movements)))
        return File(
            FileId(fileDocument.id), user, fileDocument.filename,
            movementMapper.toDomain(fileDocument.movements)
        )
    }

    override fun get(fileId: String, user: User): File {
        val fileDocument = fileRepository.findByIdAndUserId(fileId, user.id.value)
        return fileDocument.map {
            File(
                FileId(it.id), user, it.filename,
                movementMapper.toDomain(it.movements)
            )
        }.orElseThrow {
            ResourceNotFoundException("The fileId $fileId was not found for user ${user.id.value}")
        }
    }

    override fun remove(file: File) {
        fileRepository.findByIdAndUserId(file.id.value, file.user.id.value)
            .ifPresent { fileRepository.delete(it) }
    }
}