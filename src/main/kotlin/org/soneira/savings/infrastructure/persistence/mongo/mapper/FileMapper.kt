package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.PreMovement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import org.soneira.savings.domain.vo.id.FileId
import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.PreMovementDocument
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class FileMapper(private val subcategoryRepository: SubcategoryRepository) {
    fun toDomain(fileDocument: FileDocument, user: User): File {
        return File(
            FileId(fileDocument.id), user, fileDocument.filename,
            toDomain(fileDocument.movements)
        )
    }

    fun preMovementsToDocument(preMovements: List<PreMovement>): List<PreMovementDocument> {
        val subcategories = subcategoryRepository.getAll()
        val movements = mutableListOf<PreMovementDocument>()
        for (premovement in preMovements) {
            val subcategory = subcategories.first { it.descriptionSubcategory == premovement.subcategory }
            movements.add(
                PreMovementDocument(
                    premovement.operationDate, premovement.description, premovement.amount.amount,
                    premovement.order.value, subcategory.id.value, premovement.comment,
                    premovement.balance?.amount ?: BigDecimal.ZERO
                )
            )
        }
        return movements
    }

    private fun toDomain(preMovementDocuments: List<PreMovementDocument>): List<Movement> {
        val subcategories = subcategoryRepository.getAll()
        val movements = mutableListOf<Movement>()
        for (movementDocument in preMovementDocuments) {
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