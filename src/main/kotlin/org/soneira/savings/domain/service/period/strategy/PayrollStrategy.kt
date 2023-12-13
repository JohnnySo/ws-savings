package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Saving
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.Period

class PayrollStrategy(private val user: User) : PeriodStrategy {
    override fun calculateSavings(movements: List<Movement>, filename: String): List<Saving> {
        val savings = mutableListOf<Saving>()
        if (movements.isNotEmpty()) {
            val periods = getPeriods(movements)
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
     * Get the list of periods based on a payroll subcategory.
     * @param movements the list of movements [Movement]
     * @return the list of periods [Period]
     */
    private fun getPeriods(movements: List<Movement>): List<Period> {
        val payrollMovements = getPayrollMovements(movements).sortedWith(Movement.dateAndOrderComparator)
        return calculatePeriods(payrollMovements)
    }

    /**
     * Filter movements that has a subcategory defined as payroll and then removes the extra payrolls.
     * All payrolls that are between monthStartBoundary and monthEndBoundary are considered EXTRA payrolls
     * and will not be considered to create the periods
     * @param movements the list of movements [Movement]
     * @return the list of payrolls that make a period [Movement]
     */
    private fun getPayrollMovements(movements: List<Movement>): List<Movement> {
        val findPayrolls = { m: Movement -> m.subcategory == user.settings.periodDefiner }
        val removeExtraPayRolls = { p: Movement ->
            p.operationDate.dayOfMonth > user.settings.monthStartBoundary
                    && p.operationDate.dayOfMonth < user.settings.monthEndBoundary
        }
        val payrolls = movements.filter(findPayrolls).toMutableList()
        payrolls.removeAll(removeExtraPayRolls)
        return payrolls
    }

    /**
     * Calculates the list of periods based on a ordered list of payrolls.
     * The first item create a period and the next item marks the end of first period and the start of the next.
     * @param payrolls the payrolls without EXTRA payrolls ordered by operationDate [Movement]
     * @return the list of periods [Period]
     */
    private fun calculatePeriods(payrolls: List<Movement>): List<Period> {
        val periods = mutableListOf<Period>()
        val payrollsIterator = payrolls.listIterator()
        do {
            val period = Period(payrollsIterator.next().operationDate)
            if (payrollsIterator.hasNext()) {
                period.end = payrollsIterator.next().operationDate.minusDays(1)
            }
            periods.add(period)
        } while (payrollsIterator.hasNext())
        return periods
    }
}