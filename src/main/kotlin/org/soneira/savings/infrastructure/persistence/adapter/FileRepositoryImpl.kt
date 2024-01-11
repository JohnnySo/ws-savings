package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.exception.ResourceNotFoundException
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.soneira.savings.infrastructure.persistence.mongo.mapper.FileMapper
import org.soneira.savings.infrastructure.persistence.mongo.mapper.MovementMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.FileMongoRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class FileRepositoryImpl(
    private val fileRepository: FileMongoRepository,
    private val fileMapper: FileMapper,
    private val movementMapper: MovementMapper
) : FileRepository {
    override fun save(user: User, filename: String, movements: List<Movement>): File {
        val fileDocument =
            fileRepository.save(FileDocument(user.id.value, filename, movements.map { movementMapper.toDocument(it) }))
        return fileMapper.toDomain(fileDocument, user)
    }

    @Transactional(readOnly = true)
    override fun get(fileId: String, user: User): File {
        val fileDocument = fileRepository.findByIdAndUserId(fileId, user.id.value)
        return fileDocument.map { fileMapper.toDomain(it, user) }.orElseThrow {
            ResourceNotFoundException("The fileId $fileId was not found for user ${user.id.value}")
        }
    }

    override fun remove(file: File) {
        fileRepository.findByIdAndUserId(file.id.value, file.user.id.value)
            .ifPresent { fileRepository.delete(it) }
    }
}