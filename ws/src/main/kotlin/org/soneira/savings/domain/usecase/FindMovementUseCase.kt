package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.Movement

interface FindMovementUseCase {

    /**
     * Retrieves a list of movements that match the specified criteria within the description or comment fields.
     *
     * @param searchParam The search parameter used to identify relevant movements.
     * @return A list of movements that satisfy the specified criteria.
     */
    fun find(searchParam: String): List<Movement>
}