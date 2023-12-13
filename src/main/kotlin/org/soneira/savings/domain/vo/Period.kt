package org.soneira.savings.domain.vo

import org.soneira.savings.domain.exception.DomainException
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth

/**
 * This class represents one economic month. it is a default calculation by month or by payroll.
 */
data class Period(val start: LocalDate) {
    var end: LocalDate? = null
    val yearMonth: YearMonth = getLogicalPeriod()
    private fun isFirstHalfOfMonth(localDate: LocalDate) = localDate.dayOfMonth <= 15

    /**
     * The payroll could be received before the start of the month and the next payroll could
     * be received after the month so this function calculates de logical period between the start and the end.
     *
     * @return the logical period
     */
    private fun getLogicalPeriod(): YearMonth {
        return if (end != null) {
            val diff = Period.between(start.withDayOfMonth(1), end!!.withDayOfMonth(1))
            if (diff.months > 2 || diff.years >= 1) {
                throw DomainException("One of the periods is too large.")
            } else {
                if (end!!.month.value - start.month.value == 0) {
                    YearMonth.of(start.year, start.month)
                } else if (end!!.month.value - start.month.value == 1) {
                    if (isFirstHalfOfMonth(end!!) && !isFirstHalfOfMonth(this.start)) {
                        YearMonth.of(end!!.year, end!!.month).minusMonths(1)
                    } else if (!isFirstHalfOfMonth(end!!) && !isFirstHalfOfMonth(start)) {
                        YearMonth.of(end!!.year, end!!.month)
                    } else {
                        YearMonth.of(start.year, start.month)
                    }
                } else {
                    YearMonth.of(start.year, start.month).plusMonths(1)
                }
            }
        } else {
            if (isFirstHalfOfMonth(this.start)) {
                YearMonth.of(start.year, start.month)
            } else {
                YearMonth.of(start.year, start.month.plus(1))
            }
        }
    }
}