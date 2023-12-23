package org.soneira.savings.domain.service.impl

import org.soneira.savings.domain.event.PeriodCreatedEvent
import org.soneira.savings.domain.service.PeriodCreator
import org.soneira.savings.domain.vo.params.PeriodCreatorParams

class PeriodCreatorImpl : PeriodCreator {
    override fun create(periodCreatorParams: PeriodCreatorParams): PeriodCreatedEvent {
        val periodStrategy = periodCreatorParams.user.settings.periodStrategyType.get(periodCreatorParams.user)
        val periods = periodStrategy.execute(periodCreatorParams.movements,
            periodCreatorParams.filename, periodCreatorParams.optLastPeriod)
        //TODO: LANZAR EVENTO PeriodCreatedEvent
        return PeriodCreatedEvent(periods)
    }
}