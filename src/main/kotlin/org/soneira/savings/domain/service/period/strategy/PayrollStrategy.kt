package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Saving
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.EconomicalPeriod
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class PayrollStrategy(private val user: User) : PeriodStrategy {

    private val filterPayrolls = { m: Movement -> m.subcategory == user.settings.periodDefiner }

    override fun calculateSavings(movements: List<Movement>,
                                  filename: String,
                                  optLastSaving: Optional<Saving>): List<Saving> {
        val savings = mutableListOf<Saving>()
        if (movements.isNotEmpty()) {
            optLastSaving.ifPresent { lastSaving -> savings.add(completeLastSaving(lastSaving, movements)) }
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
     * Completes the last period that is incomplete from a previous importation
     *
     * @param lastSaving last period
     * @param movements list of all movements to be imported
     * @return the saving updated
     */
    private fun completeLastSaving(lastSaving: Saving, movements: List<Movement>): Saving {
        val allMovements = lastSaving.movements.toMutableList()
        val maxOrder = lastSaving.getMaxOrder()
        val nextPayroll = movements.firstOrNull(filterPayrolls)
        val nextEndDate : LocalDate
        if (nextPayroll == null) {
            val ym = YearMonth.of(lastSaving.economicalPeriod.start.year, lastSaving.economicalPeriod.start.month)
            nextEndDate = ym.atEndOfMonth()
        } else {
            nextEndDate = nextPayroll.operationDate.minusDays(1)
        }
        val movementsToAdd = movements
            .filter { m -> m.isDateBetween(lastSaving.economicalPeriod.start, nextEndDate) }
        allMovements.addAll(movementsToAdd)
        allMovements.forEach { m->m.updateOrder(m.order.value+maxOrder) }
        return lastSaving.copy(economicalPeriod = EconomicalPeriod(lastSaving.economicalPeriod.start, nextEndDate),
            movements = allMovements.sortedWith(Movement.dateAndOrderComparator))
    }

    /**
     * Get the list of periods based on a payroll subcategory.
     * @param movements the list of movements [Movement]
     * @return the list of periods [EconomicalPeriod]
     */
    private fun getPeriods(movements: List<Movement>): List<EconomicalPeriod> {
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
        val removeExtraPayRolls = { p: Movement ->
            p.operationDate.dayOfMonth > user.settings.monthStartBoundary
                    && p.operationDate.dayOfMonth < user.settings.monthEndBoundary
        }
        val payrolls = movements.filter(filterPayrolls).toMutableList()
        payrolls.removeAll(removeExtraPayRolls)
        return payrolls
    }

    /**
     * Calculates the list of periods based on a ordered list of payrolls.
     * The first item create a period and the next item marks the end of first period and the start of the next.
     * @param payrolls the payrolls without EXTRA payrolls ordered by operationDate [Movement]
     * @return the list of periods [EconomicalPeriod]
     */
    private fun calculatePeriods(payrolls: List<Movement>): List<EconomicalPeriod> {
        val periods = mutableListOf<EconomicalPeriod>()
        val payrollsIterator = payrolls.listIterator()
        do {
            if (payrollsIterator.hasNext()) {
                periods.add(EconomicalPeriod(payrollsIterator.next().operationDate,
                    payrollsIterator.next().operationDate.minusDays(1)))
            }else{
                val ym = YearMonth.of(payrollsIterator.next().operationDate.year,
                    payrollsIterator.next().operationDate.month)
                periods.add(EconomicalPeriod(payrollsIterator.next().operationDate, ym.atEndOfMonth()))
            }
        } while (payrollsIterator.hasNext())
        return periods
    }
}