package org.soneira.savings.domain.port.input

import org.soneira.savings.domain.vo.EditableMovement

interface EditMovementApplicationService {
    /**
     * Modify specific information for a movement, including the description, subcategory,
     * comments and order.
     *
     * @param movement The information to be edited.
     */
    fun edit(movement: EditableMovement)
}