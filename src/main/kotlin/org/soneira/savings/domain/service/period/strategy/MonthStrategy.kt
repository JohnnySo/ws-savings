package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class MonthStrategy(private val user: User) : PeriodStrategy {

    override fun execute(
        file: File,
        optLastPeriod: Optional<EconomicPeriod>
    ): List<EconomicPeriod> {
        val economicPeriods = mutableListOf<EconomicPeriod>()
        if (file.movements.isNotEmpty()) {
            optLastPeriod.ifPresent { lastPeriod ->
                economicPeriods.add(completeLastPeriod(lastPeriod, file.movements))
            }
            val periods = getPeriods(file.movements.sortedWith(Movement.dateAndOrderComparator))
            for (period in periods) {
                val economicPeriod = EconomicPeriod(user, period.key, period.value, file.filename,
                    file.movements.filter { m -> m.isDateBetween(period.key, period.value) }
                        .sortedWith(Movement.dateAndOrderComparator))
                economicPeriods.add(economicPeriod)
            }
        }
        return economicPeriods
    }

    /**
     * Completes the last period that is incomplete from a previous importation
     *
     * @param lastPeriod last period
     * @param movements list of all movements to be imported
     */
    private fun completeLastPeriod(lastPeriod: EconomicPeriod, movements: List<Movement>): EconomicPeriod {
        val allMovements = lastPeriod.movements.toMutableList()
        val maxOrder = lastPeriod.getMaxOrder()
        val movementsToAdd = movements
            .filter { m -> m.isDateBetween(lastPeriod.start, lastPeriod.end) }
        allMovements.addAll(movementsToAdd)
        allMovements.forEach { m -> m.order.value += maxOrder }
        return lastPeriod.copy(movements = allMovements.sortedWith(Movement.dateAndOrderComparator))
    }

    /**
     * Get the list of periods based on the first and last day of the movements list.
     * @param movements the list of movements ordered by date and order [Movement]
     * @return the map of periods (key -> start date; value -> end date) [MutableMap]
     */
    private fun getPeriods(movements: List<Movement>): MutableMap<LocalDate, LocalDate> {
        var firstPeriodDay = YearMonth
            .of(movements.first().operationDate.year, movements.first().operationDate.month).atDay(1)
        val lastDay = movements.last().operationDate
        val periods = mutableMapOf<LocalDate, LocalDate>()
        while (firstPeriodDay.isBefore(lastDay)) {
            val start = firstPeriodDay
            periods[start] = YearMonth.of(start.year, start.month).atEndOfMonth()
            firstPeriodDay = firstPeriodDay.plusMonths(1)
        }
        return periods
    }
}