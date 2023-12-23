package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.EconomicPeriod
import java.util.*

interface PeriodStrategy {
    /**
     * Divide the movements into periods based on the chosen strategy [PeriodStrategyFactory]
     *
     * @param movements list of movements
     * @param filename the filename where the data came from
     * @param optLastPeriod the last economic period that was ingested in the system to complete it
     * @return list of economic periods [EconomicPeriod]
     */
    fun execute(movements: List<Movement>, filename: String, optLastPeriod: Optional<EconomicPeriod>): List<EconomicPeriod>
}