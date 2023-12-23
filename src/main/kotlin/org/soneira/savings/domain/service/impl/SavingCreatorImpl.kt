package org.soneira.savings.domain.service.impl

import org.soneira.savings.domain.event.SavingsCreatedEvent
import org.soneira.savings.domain.service.SavingCreator
import org.soneira.savings.domain.vo.params.SavingsCreatorParams

class SavingCreatorImpl : SavingCreator {
    override fun create(savingsCreatorParams: SavingsCreatorParams): SavingsCreatedEvent {
        val periodStrategy = savingsCreatorParams.user.settings.periodStrategyType.get(savingsCreatorParams.user)
        val savings = periodStrategy.calculateSavings(savingsCreatorParams.movements,
            savingsCreatorParams.filename, savingsCreatorParams.optLastSaving)
        //TODO: LANZAR EVENTO SAVINGS CREATED EVENT
        return SavingsCreatedEvent(savings)
    }
}