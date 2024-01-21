package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.port.output.repository.MovementRepository
import org.soneira.savings.domain.vo.EditableMovement
import org.soneira.savings.domain.vo.id.UserId
import org.springframework.stereotype.Component

@Component
class MovementRepositoryImpl : MovementRepository {
    override fun find(userId: UserId, searchParam: String): List<Movement> {
        TODO("Not yet implemented")
    }

    override fun edit(userId: UserId, movement: EditableMovement) {
        TODO("Not yet implemented")
    }
}