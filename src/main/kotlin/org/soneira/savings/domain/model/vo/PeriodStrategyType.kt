package org.soneira.savings.domain.model.vo

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.service.strategy.MonthStrategy
import org.soneira.savings.domain.service.strategy.PayrollStrategy
import org.soneira.savings.domain.service.strategy.PeriodStrategy
import org.soneira.savings.domain.service.strategy.PeriodStrategyFactory

enum class PeriodStrategyType : PeriodStrategyFactory {
    PAYROLL {
        override fun get(user: User): PeriodStrategy {
            return PayrollStrategy(user)
        }
    },
    MONTH {
        override fun get(user: User): PeriodStrategy {
            return MonthStrategy(user)
        }
    }
}