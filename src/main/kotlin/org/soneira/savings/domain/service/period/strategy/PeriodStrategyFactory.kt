package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.User

interface PeriodStrategyFactory {
    fun get(user: User): PeriodStrategy
}