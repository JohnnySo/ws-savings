package org.soneira.savings.domain.port.input

import org.soneira.savings.domain.entity.Movement

interface FindMovementApplicationService {

    /**
     * Retrieves a list of movements that match the specified criteria within the description or comment fields.
     *
     * @param searchParam The search parameter used to identify relevant movements.
     * @return A list of movements that satisfy the specified criteria.
     */
    fun find(searchParam: String): List<Movement>
}