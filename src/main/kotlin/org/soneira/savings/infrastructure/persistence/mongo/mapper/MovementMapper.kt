package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import org.soneira.savings.domain.vo.id.MovementId
import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.springframework.stereotype.Component

@Component
class MovementMapper(private val subCategoryRepository: SubcategoryRepository) {
    fun toDocument(movement: Movement): MovementDocument {
        return MovementDocument(
            movement.operationDate,
            movement.description,
            movement.amount.amount,
            movement.order.value,
            movement.subcategory.id.value,
            movement.comment,
            movement.balance.amount
        )
    }

    fun toDomain(movement: MovementDocument): Movement {
        return Movement(
            MovementId(movement.id),
            movement.operationDate,
            movement.description,
            Money.of(movement.amount),
            Order(movement.order),
            subCategoryRepository.getAll().first { it.id.value == movement.subcategory },
            movement.comment,
            Money.of(movement.balance)
        )
    }

    fun toDomainWithoutId(movement: MovementDocument): Movement {
        return Movement(
            movement.operationDate,
            movement.description,
            Money.of(movement.amount),
            Order(movement.order),
            subCategoryRepository.getAll().first { it.id.value == movement.subcategory },
            movement.comment,
            Money.of(movement.balance)
        )
    }
}