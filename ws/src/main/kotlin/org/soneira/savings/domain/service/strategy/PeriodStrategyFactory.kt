package org.soneira.savings.domain.service.strategy

import org.soneira.savings.domain.model.entity.User

interface PeriodStrategyFactory {
    fun get(user: User): PeriodStrategy
}