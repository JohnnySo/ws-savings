package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Saving
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.EconomicalPeriod
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class MonthStrategy(val user: User) : PeriodStrategy {

    override fun calculateSavings(movements: List<Movement>,
                                  filename: String,
                                  optLastSaving: Optional<Saving>): List<Saving> {
        val savings = mutableListOf<Saving>()
        if(movements.isNotEmpty()) {
            optLastSaving.ifPresent { lastSaving -> savings.add(completeLastSaving(lastSaving, movements)) }
            val periods = getPeriods(movements.sortedWith(Movement.dateAndOrderComparator))
            for (period in periods) {
                val saving = Saving(user, period, filename,
                    movements.filter { m -> m.isDateBetween(period.start, period.end) }
                        .sortedWith(Movement.dateAndOrderComparator))
                savings.add(saving)
            }
        }
        return savings
    }

    /**
     * Completes the last period that is incomplete from a previous importation
     *
     * @param lastSaving last period
     * @param movements list of all movements to be imported
     */
    private fun completeLastSaving(lastSaving: Saving, movements: List<Movement>): Saving {
        val allMovements = lastSaving.movements.toMutableList()
        val maxOrder = lastSaving.getMaxOrder()
        val movementsToAdd = movements
            .filter { m -> m.isDateBetween(lastSaving.economicalPeriod.start, lastSaving.economicalPeriod.end) }
        allMovements.addAll(movementsToAdd)
        allMovements.forEach { m->m.updateOrder(m.order.value+maxOrder) }
        return lastSaving.copy(movements = allMovements.sortedWith(Movement.dateAndOrderComparator))
    }

    /**
     * Get the list of periods based on the first and last day of the movements list.
     * @param movements the list of movements ordered by date and order [Movement]
     * @return the list of periods [EconomicalPeriod]
     */
    private fun getPeriods(movements: List<Movement>): List<EconomicalPeriod> {
        var firstPeriodDay = YearMonth
            .of(movements.first().operationDate.year, movements.first().operationDate.month).atDay(1)
        val lastDay = movements.last().operationDate
        val periods = mutableListOf<EconomicalPeriod>()
        while (firstPeriodDay.isBefore(lastDay)) {
            val period = EconomicalPeriod(firstPeriodDay, YearMonth.of(firstPeriodDay.year, firstPeriodDay.month)
                .atEndOfMonth())
            periods.add(period)
            firstPeriodDay = firstPeriodDay.plusMonths(1)
        }
        return periods
    }
}