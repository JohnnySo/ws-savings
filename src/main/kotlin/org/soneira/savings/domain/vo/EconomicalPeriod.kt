package org.soneira.savings.domain.vo

import org.soneira.savings.domain.exception.DomainException
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth

/**
 * This class represents one economic period.
 * If the strategy is by payroll then the period start with the day of the payroll and end with the date of the next
 * payroll.
 * If the strategy is by month then the period is equals to start and end of month
 */
data class EconomicalPeriod(val start: LocalDate, val end: LocalDate) {
    val yearMonth: YearMonth = getLogicalPeriod()

    /**
     * The payroll could be received before the start of the month and the next payroll could
     * be received after the month so this function calculates de logical period between the start and the end.
     *
     * @return the logical period
     */
    private fun getLogicalPeriod(): YearMonth {
    val diff = Period.between(start.withDayOfMonth(1), end.withDayOfMonth(1))
    return if (diff.months > 2 || diff.years >= 1) {
            throw DomainException("One of the periods is too large.")
    } else {
        if (end.month.value - start.month.value == 0) {
            YearMonth.of(start.year, start.month)
        } else if (end.month.value - start.month.value == 1) {
            if (isFirstHalfOfMonth(end) && !isFirstHalfOfMonth(this.start)) {
                YearMonth.of(end.year, end.month).minusMonths(1)
            } else if (!isFirstHalfOfMonth(end) && !isFirstHalfOfMonth(start)) {
                YearMonth.of(end.year, end.month)
            } else {
                YearMonth.of(start.year, start.month)
            }
        } else {
            YearMonth.of(start.year, start.month).plusMonths(1)
        }
    }
    }

    companion object{
        /**
         * Get the next period of a given date
         */
        fun getNextPeriod(fromThisDate: LocalDate): EconomicalPeriod {
            return if(isFirstHalfOfMonth(fromThisDate)){
                EconomicalPeriod(fromThisDate, YearMonth.of(fromThisDate.year, fromThisDate.month).atEndOfMonth())
            }else{
                EconomicalPeriod(fromThisDate, YearMonth.of(fromThisDate.year, fromThisDate.month)
                    .plusMonths(1).atEndOfMonth())
            }
        }

        /**
         * If the day of month of the localDate passed as parameter is less or equal dan 15 return true.
         */
        private fun isFirstHalfOfMonth(localDate: LocalDate) = localDate.dayOfMonth <= 15
    }
}