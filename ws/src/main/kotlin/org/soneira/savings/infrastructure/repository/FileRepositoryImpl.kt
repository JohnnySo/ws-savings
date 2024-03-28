package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.exception.ResourceNotFoundException
import org.soneira.savings.domain.model.vo.id.FileId
import org.soneira.savings.domain.repository.FileRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.soneira.savings.infrastructure.persistence.mongo.mapper.FileMapper
import org.soneira.savings.infrastructure.persistence.mongo.mapper.MovementMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.FileMongoRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FileRepositoryImpl(
    private val fileMongodbRepository: FileMongoRepository,
    private val fileMapper: FileMapper,
    private val movementMapper: MovementMapper
) : FileRepository {

    @Transactional
    override fun save(user: User, filename: String, movements: List<Movement>): File {
        val fileDocument = fileMongodbRepository.save(
            FileDocument(user.id.value, filename,
                movements
                    .sortedWith(Movement.dateAndOrderComparator)
                    .map { movementMapper.asMovementDocument(it, user.id.value) })
        )
        return fileMapper.asFile(fileDocument, user)
    }

    override fun get(id: FileId, user: User): File {
        val fileDocument = fileMongodbRepository.findByIdAndUser(id.value, user.id.value)
        return fileDocument.map { fileMapper.asFile(it, user) }.orElseThrow {
            ResourceNotFoundException("The fileId $id was not found for user ${user.id.value}")
        }
    }

    override fun getAll(user: User): List<File> {
        return fileMongodbRepository.findAll().map { fileMapper.asFile(it, user) }
    }

    @Transactional
    override fun remove(file: File) {
        fileMongodbRepository.findByIdAndUser(file.id.value, file.user.id.value)
            .ifPresent { fileMongodbRepository.delete(it) }
    }
}