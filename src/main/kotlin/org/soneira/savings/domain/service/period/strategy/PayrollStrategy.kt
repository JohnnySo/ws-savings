package org.soneira.savings.domain.service.period.strategy

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.User
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class PayrollStrategy(private val user: User) : PeriodStrategy {

    private val filterPayrolls = { m: Movement -> m.subcategory == user.settings.periodDefiner }

    override fun execute(movements: List<Movement>,
                         filename: String,
                         optLastPeriod: Optional<EconomicPeriod>): List<EconomicPeriod> {
        val economicPeriods = mutableListOf<EconomicPeriod>()
        if (movements.isNotEmpty()) {
            optLastPeriod.ifPresent { lastPeriod -> economicPeriods.add(completeLastPeriod(lastPeriod, movements)) }
            val periods = getPeriods(movements)
            for (period in periods) {
                val economicPeriod = EconomicPeriod(user, period.key, period.value, filename,
                    movements.filter { m -> m.isDateBetween(period.key, period.value) }
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
     * @return the period updated
     */
    private fun completeLastPeriod(lastPeriod: EconomicPeriod, movements: List<Movement>): EconomicPeriod {
        val allMovements = lastPeriod.movements.toMutableList()
        val maxOrder = lastPeriod.getMaxOrder()
        val nextPayroll = movements.firstOrNull(filterPayrolls)
        val nextEndDate : LocalDate
        if (nextPayroll == null) {
            val ym = YearMonth.of(lastPeriod.start.year, lastPeriod.start.month)
            nextEndDate = ym.atEndOfMonth()
        } else {
            nextEndDate = nextPayroll.operationDate.minusDays(1)
        }
        val movementsToAdd = movements
            .filter { m -> m.isDateBetween(lastPeriod.start, nextEndDate) }
        allMovements.addAll(movementsToAdd)
        allMovements.forEach { m->m.updateOrder(m.order.value+maxOrder) }
        return lastPeriod.copy(end = nextEndDate, movements = allMovements.sortedWith(Movement.dateAndOrderComparator))
    }

    /**
     * Get the list of periods based on a payroll subcategory.
     * @param movements the list of movements [Movement]
     * @return the map of periods (key -> start date; value -> end date) [MutableMap]
     */
    private fun getPeriods(movements: List<Movement>): MutableMap<LocalDate, LocalDate> {
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
     * @return the map of periods (key -> start date; value -> end date) [MutableMap]
     */
    private fun calculatePeriods(payrolls: List<Movement>): MutableMap<LocalDate, LocalDate> {
        val periods = mutableMapOf<LocalDate, LocalDate>()
        val payrollsIterator = payrolls.listIterator()
        do {
            if (payrollsIterator.hasNext()) {
                periods[payrollsIterator.next().operationDate] =
                    payrollsIterator.next().operationDate.minusDays(1)
            }else{
                val ym = YearMonth.of(payrollsIterator.next().operationDate.year,
                    payrollsIterator.next().operationDate.month)
                periods[payrollsIterator.next().operationDate] = ym.atEndOfMonth()
            }
        } while (payrollsIterator.hasNext())
        return periods
    }
}