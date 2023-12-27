package org.soneira.savings.domain.service.impl

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.event.PeriodCreatedEvent
import org.soneira.savings.domain.service.PeriodCreator
import java.util.*

class PeriodCreatorImpl : PeriodCreator {
    override fun create(user: User, file: File, optLastPeriod: Optional<EconomicPeriod>
    ): PeriodCreatedEvent {
        val periodStrategy = user.settings.periodStrategyType.get(user)
        val periods = periodStrategy.execute(file, optLastPeriod)
        return PeriodCreatedEvent(file, periods)
    }
}