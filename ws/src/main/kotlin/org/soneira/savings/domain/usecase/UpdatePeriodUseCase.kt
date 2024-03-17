package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.User

interface UpdatePeriodUseCase {

    /**
     * Recalculates all information for a period of a specific movement [EconomicPeriod]
     * @param user the user
     * @param movement the edited movement
     */
    fun update(user: User, movement: Movement)
}