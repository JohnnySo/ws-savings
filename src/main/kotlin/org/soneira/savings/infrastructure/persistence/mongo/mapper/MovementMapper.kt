package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Order
import org.soneira.savings.domain.model.vo.id.MovementId
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.springframework.stereotype.Component

@Component
class MovementMapper(private val subCategoryRepository: SubcategoryRepository) {
    fun toDocument(movement: Movement, user: String): MovementDocument {
        if (movement.isIdInit()) {
            return MovementDocument(
                movement.id.value,
                movement.operationDate,
                movement.description,
                movement.amount.amount,
                movement.order.value,
                movement.subcategory.id.value,
                movement.comment,
                movement.balance.amount,
                user
            )
        } else {
            return MovementDocument(
                movement.operationDate,
                movement.description,
                movement.amount.amount,
                movement.order.value,
                movement.subcategory.id.value,
                movement.comment,
                movement.balance.amount,
                user
            )
        }
    }

    fun toDomain(movement: MovementDocument): Movement {
        if (movement.isIdInit()) {
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
        } else {
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
}