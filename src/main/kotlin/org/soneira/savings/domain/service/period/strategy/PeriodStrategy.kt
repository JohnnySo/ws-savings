package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Saving

interface PeriodStrategy {
    /**
     * Divide the movements into periods based on the chosen strategy [PeriodStrategyFactory]
     *
     * @param movements list of movements
     * @param filename the filename where the data came from
     * @return list of saving periods [Saving]
     */
    fun calculateSavings(movements: List<Movement>, filename: String): List<Saving>
}