package org.soneira.savings.domain.service

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.event.SavingsCreatedEvent

class SavingDomainServiceImpl : SavingsDomainService {
    override fun createSavings(user: User, filename: String, movements: List<Movement>): SavingsCreatedEvent {
        val periodStrategy = user.settings.periodStrategyType.get(user)
        val savings = periodStrategy.calculateSavings(movements, filename)
        //TODO: LANZAR EVENTO SAVINGS CREATED EVENT
        return SavingsCreatedEvent(savings)
    }
}