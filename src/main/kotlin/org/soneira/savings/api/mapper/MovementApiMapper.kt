package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.CategoryDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.dto.SubcategoryDTO
import org.soneira.savings.domain.entity.Category
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Subcategory
import org.springframework.stereotype.Component

@Component
class MovementApiMapper {
    fun toDto(movement: Movement): MovementDTO {
        return MovementDTO(
            movement.id.value, movement.operationDate, movement.description,
            movement.amount.amount, toDto(movement.subcategory.category), toDto(movement.subcategory),
            movement.comment, movement.balance.amount, movement.order.value
        )
    }

    fun toDto(category: Category): CategoryDTO {
        return CategoryDTO(category.id.value, category.descriptionEs)
    }

    fun toDto(subcategory: Subcategory): SubcategoryDTO {
        return SubcategoryDTO(subcategory.id.value, subcategory.descriptionEs)
    }
}