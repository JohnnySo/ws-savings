package org.soneira.savings.domain.port.input

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User

interface UpdatePeriodApplicationService {

    /**
     * Recalculates all information for a period of a specific movement [EconomicPeriod]
     * @param user the user
     * @param movement the edited movement
     */
    fun update(user: User, movement: Movement)
}