package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.vo.EditableMovement
import org.soneira.savings.domain.vo.id.UserId

interface MovementRepository {

    /**
     * Find a list of movements that match the specified criteria within the description or comment fields.
     *
     * @param userId the user identifier
     * @param searchParam The search parameter used to identify relevant movements.
     * @return A list of movements that satisfy the specified criteria.
     */
    fun find(userId: UserId, searchParam: String): List<Movement>

    /**
     * Modify specific information for a movement, including the description, subcategory,
     * comments and order.
     *
     * @param userId the user identifier
     * @param movement The information to be edited.
     */
    fun edit(userId: UserId, movement: EditableMovement)
}