package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.exception.ResourceNotFoundException
import org.soneira.savings.domain.model.vo.EditableMovement
import org.soneira.savings.domain.repository.MovementRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.MovementMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.CustomMongoRepositoryImpl
import org.soneira.savings.infrastructure.persistence.mongo.repository.MovementMongoRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation.REQUIRED
import org.springframework.transaction.annotation.Transactional

@Component
class MovementRepositoryImpl(
    val movementMongoRepository: MovementMongoRepository,
    val customMongoRepositoryImpl: CustomMongoRepositoryImpl,
    val movementMapper: MovementMapper
) : MovementRepository {
    
    override fun find(user: User, searchParam: String): List<Movement> {
        val movements = customMongoRepositoryImpl.searchMovements(user.id.value, searchParam)
        return movements.map { movementMapper.toDomain(it) }
    }

    @Transactional(propagation = REQUIRED)
    override fun edit(user: User, editableMovement: EditableMovement): Movement {
        val movementDocument = movementMongoRepository.findByUserAndId(user.id.value, editableMovement.id.value)
            .orElseThrow { ResourceNotFoundException("The movement with id ${editableMovement.id.value} do not exist.") }
        editableMovement.description?.let { movementDocument.description = it }
        editableMovement.comment?.let { movementDocument.comment = it }
        editableMovement.subcategory?.let { movementDocument.subcategory = it.id.value }
        movementMapper.toDomain(movementMongoRepository.save(movementDocument))
        return movementMapper.toDomain(movementMongoRepository.save(movementDocument))
    }
}