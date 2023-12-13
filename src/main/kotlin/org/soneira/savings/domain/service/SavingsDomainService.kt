package org.soneira.savings.domain.service

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.event.SavingsCreatedEvent

interface SavingsDomainService {

    /**
     * Divides the list of movements into periods called savings.
     * It calculates the total income, expense and saved of each period.
     * @param user the user proprietary of the data
     * @param filename where the data came from
     * @param movements the list of movements to incorporate to the saving service
     * @return All the movements divided into saving periods and with the totals calculated
     */
    fun createSavings(user: User, filename: String, movements: List<Movement>): SavingsCreatedEvent
}