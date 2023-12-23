package org.soneira.savings.domain.service

import org.soneira.savings.domain.event.PeriodCreatedEvent
import org.soneira.savings.domain.vo.params.PeriodCreatorParams

interface PeriodCreator {

    /**
     * Divides the list of movements into periods called economicPeriods.
     * It calculates the total income, expense and saved of each period.
     * The list must be ordered by operationDate
     * @param periodCreatorParams the params needed to calculate the economicPeriods
     * @return All the movements divided into economic periods and with the totals calculated
     */
    fun create(periodCreatorParams: PeriodCreatorParams): PeriodCreatedEvent
}