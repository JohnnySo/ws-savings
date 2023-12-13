package org.soneira.savings.domain.vo

import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.service.period.strategy.MonthStrategy
import org.soneira.savings.domain.service.period.strategy.PayrollStrategy
import org.soneira.savings.domain.service.period.strategy.PeriodStrategy
import org.soneira.savings.domain.service.period.strategy.PeriodStrategyFactory

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