package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Saving
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.Period
import java.time.YearMonth

class MonthStrategy(val user: User) : PeriodStrategy {
    override fun calculateSavings(movements: List<Movement>, filename: String): List<Saving> {
        val savings = mutableListOf<Saving>()
        val periods = getPeriods(movements.sortedWith(Movement.dateAndOrderComparator))
        for (period in periods) {
            val saving = Saving(user, period, filename,
                movements.filter { m -> m.isDateBetween(period.start, period.end) }
                    .sortedWith(Movement.dateAndOrderComparator))
            savings.add(saving)
        }
        return savings
    }

    /**
     * Get the list of periods based on the first and last day of the movements list.
     * @param movements the list of movements ordered by date and order [Movement]
     * @return the list of periods [Period]
     */
    private fun getPeriods(movements: List<Movement>): List<Period> {
        var firstPeriodDay = YearMonth
            .of(movements.first().operationDate.year, movements.first().operationDate.month).atDay(1)
        val lastDay = movements.last().operationDate
        val periods = mutableListOf<Period>()
        while (firstPeriodDay.isBefore(lastDay)) {
            val period = Period(firstPeriodDay)
            period.end = YearMonth.of(firstPeriodDay.year, firstPeriodDay.month).atEndOfMonth()
            periods.add(period)
            firstPeriodDay = firstPeriodDay.plusMonths(1)
        }
        return periods
    }
}