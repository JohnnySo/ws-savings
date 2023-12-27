package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File
import java.util.*

sealed interface PeriodStrategy {
    /**
     * Divide the movements into periods based on the chosen strategy [PeriodStrategyFactory]
     *
     * @param movements list of movements
     * @param filename the filename where the data came from
     * @param optLastPeriod the last economic period that was ingested in the system to complete it
     * @return list of economic periods [EconomicPeriod]
     */
    fun execute(file: File,
                optLastPeriod: Optional<EconomicPeriod>): List<EconomicPeriod>
}