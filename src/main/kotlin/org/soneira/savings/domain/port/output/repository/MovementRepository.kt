package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.EditableMovement

interface MovementRepository {

    /**
     * Find a list of movements that match the specified criteria within the description or comment fields.
     *
     * @param user the user
     * @param searchParam The search parameter used to identify relevant movements.
     * @return A list of movements that satisfy the specified criteria.
     */
    fun find(user: User, searchParam: String): List<Movement>

    /**
     * Modify specific information for a movement, including the description, subcategory,
     * comments and order.
     *
     * @param user the user
     * @param editableMovement The information to be edited.
     * @return The movement updated
     */
    fun edit(user: User, editableMovement: EditableMovement): Movement
}