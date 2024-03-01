package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.CategoryDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.dto.SubcategoryDTO
import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.vo.EditableMovement
import org.soneira.savings.domain.model.vo.Order
import org.soneira.savings.domain.model.vo.id.MovementId
import org.soneira.savings.domain.model.vo.id.SubcategoryId
import org.springframework.stereotype.Component

@Component
class MovementApiMapper {
    fun asMovementDTO(movement: Movement): MovementDTO {
        return MovementDTO(
            movement.id.value,
            movement.operationDate,
            movement.description,
            movement.amount.amount,
            asCategoryDTO(movement.subcategory.category),
            asSubcategoryDTO(movement.subcategory),
            movement.comment,
            movement.balance.amount,
            movement.order.value
        )
    }

    fun asEditableMovement(movement: MovementDTO): EditableMovement {
        return EditableMovement(
            MovementId(movement.id),
            movement.description,
            Subcategory(SubcategoryId(movement.subcategory.id)),
            movement.comment, Order(movement.order)
        )
    }

    private fun asCategoryDTO(category: Category): CategoryDTO {
        return CategoryDTO(category.id.value, category.descriptionEs)
    }

    private fun asSubcategoryDTO(subcategory: Subcategory): SubcategoryDTO {
        return SubcategoryDTO(subcategory.id.value, subcategory.descriptionEs)
    }
}