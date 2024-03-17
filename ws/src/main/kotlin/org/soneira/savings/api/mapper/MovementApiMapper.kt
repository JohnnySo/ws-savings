package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.dto.MovementViewDTO
import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.vo.EditableMovement
import org.soneira.savings.domain.model.vo.id.MovementId
import org.soneira.savings.domain.model.vo.id.SubcategoryId
import org.springframework.stereotype.Component

@Component
class MovementApiMapper(val categoryApiMapper: CategoryApiMapper) {
    fun asMovementViewDTO(movement: Movement): MovementViewDTO {

        val movementDTO = MovementViewDTO(
            movement.operationDate,
            movement.description,
            movement.amount.amount,
            categoryApiMapper.asCategoryDTO(movement.subcategory.category),
            categoryApiMapper.asSubcategoryDTO(movement.subcategory),
            movement.comment,
            movement.balance.amount,
            movement.order.value
        )
        if (movement.isIdInit()) {
            movementDTO.id = movement.id.value
        }
        return movementDTO
    }

    fun asEditableMovement(movementDTO: MovementDTO): EditableMovement {
        val editableMovement = EditableMovement(MovementId(movementDTO.id))
        editableMovement.description = movementDTO.description
        movementDTO.subcategory?.let {
            editableMovement.subcategory = Subcategory(SubcategoryId(it))
        }
        editableMovement.comment = movementDTO.comment
        return editableMovement
    }
}