package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.vo.EditableMovement

interface EditMovementUseCase {
    /**
     * Modify specific information for a movement, including the description, subcategory,
     * comments and order.
     *
     * @param movement The information to be edited.
     */
    fun edit(movement: EditableMovement)
}