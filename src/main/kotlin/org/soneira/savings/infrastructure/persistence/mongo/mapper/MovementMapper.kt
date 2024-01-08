package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.PreMovement
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class MovementMapper(private val subcategoryRepository: SubcategoryRepository) {
    fun preMovementsToDocument(preMovements: List<PreMovement>): List<MovementDocument> {
        val subcategories = subcategoryRepository.getAll()
        val movements = mutableListOf<MovementDocument>()
        for (premovement in preMovements) {
            val subcategory = subcategories.first { it.descriptionSubcategory == premovement.subcategory }
            movements.add(
                MovementDocument(
                    premovement.operationDate, premovement.description, premovement.amount.amount,
                    premovement.order.value, subcategory.id.value, premovement.comment,
                    premovement.balance?.amount ?: BigDecimal.ZERO
                )
            )
        }
        return movements
    }

    fun toDomain(movementDocuments: List<MovementDocument>): List<Movement> {
        val subcategories = subcategoryRepository.getAll()
        val movements = mutableListOf<Movement>()
        for (movementDocument in movementDocuments) {
            val subcategory = subcategories.first { it.id.value == movementDocument.subcategory }
            movements.add(
                Movement(
                    movementDocument.operationDate, movementDocument.description, Money.of(movementDocument.amount),
                    Order(movementDocument.order), subcategory, movementDocument.comment,
                    Money.of(movementDocument.balance ?: BigDecimal.ZERO)
                )
            )
        }
        return movements
    }
}