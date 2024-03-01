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

    @Transactional(readOnly = true)
    override fun find(user: User, searchParam: String): List<Movement> {
        val movements = customMongoRepositoryImpl.searchMovements(user.id.value, searchParam)
        return movements.map { movementMapper.toDomain(it) }
    }

    @Transactional(propagation = REQUIRED)
    override fun edit(user: User, editableMovement: EditableMovement): Movement {
        val optMovement = movementMongoRepository.findByUserAndId(user.id.value, editableMovement.id.value)
        return if (optMovement.isPresent) {
            val movement = optMovement.get()
            movement.description = editableMovement.description
            movement.subcategory = editableMovement.subcategory.id.value
            movement.comment = editableMovement.comment
            movementMapper.toDomain(movementMongoRepository.save(movement))
        } else {
            throw ResourceNotFoundException("The movement with id ${editableMovement.id.value} do not exist.")
        }
    }
}