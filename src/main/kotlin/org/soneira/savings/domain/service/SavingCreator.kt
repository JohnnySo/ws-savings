package org.soneira.savings.domain.service

import org.soneira.savings.domain.event.SavingsCreatedEvent
import org.soneira.savings.domain.vo.params.SavingsCreatorParams

interface SavingCreator {

    /**
     * Divides the list of movements into periods called savings.
     * It calculates the total income, expense and saved of each period.
     * The list must be ordered by operationDate
     * @param savingsCreatorParams the params needed to calculate the savings
     * @return All the movements divided into saving periods and with the totals calculated
     */
    fun create(savingsCreatorParams: SavingsCreatorParams): SavingsCreatedEvent
}