package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.port.output.repository.MovementRepository
import org.soneira.savings.domain.vo.EditableMovement
import org.soneira.savings.infrastructure.persistence.mongo.mapper.MovementMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.CustomMongoRepositoryImpl
import org.springframework.stereotype.Component

@Component
class MovementRepositoryImpl(
    val customMongoRepositoryImpl: CustomMongoRepositoryImpl,
    val movementMapper: MovementMapper
) : MovementRepository {
    override fun find(user: User, searchParam: String): List<Movement> {
        val movements = customMongoRepositoryImpl.searchMovements(user.id.value, searchParam)
        return movements.map { movementMapper.toDomain(it) }
    }

    override fun edit(user: User, movement: EditableMovement) {
        TODO("Not yet implemented")
    }
}