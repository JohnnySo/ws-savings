package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.AggregateRoot
import org.soneira.savings.domain.exception.DomainException
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Totals
import org.soneira.savings.domain.vo.id.PeriodId
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth

/**
 * This class represents one economic period.
 * If the strategy is by payroll then the period start with the day of the payroll
 * and end with the date of the next payroll.
 * If the strategy is by month then the period is equals to start and end of month
 */
data class EconomicPeriod(
    val user: User,
    val start: LocalDate,
    val end: LocalDate,
    val filename: String,
    val movements: List<Movement>,
) : AggregateRoot<PeriodId>() {
    private val totals: Totals
    private val expenseByCategory: Map<Category, Money>
    private val expenseBySubcategory: Map<Subcategory, Money>
    private val yearMonth: YearMonth

    init {
        restoreOrderMovements()
        totals = calculateTotals()
        expenseByCategory = calculateExpensesByCategory()
        expenseBySubcategory = calculateExpensesBySubCategory()
        yearMonth = getLogicalPeriod()
    }

    public fun getMaxOrder(): Int {
        val max = movements.maxByOrNull { m-> m.order }
        return max?.order?.value ?: 0
    }

    private fun restoreOrderMovements() {
        val min = movements.minByOrNull { it.order }
        if (min != null) {
            for (movement in movements) {
                movement.order.value -= min.order.value.minus(1)
            }
        }
    }

    private fun calculateTotals(): Totals {
        val totals = Totals(Money.ZERO, Money.ZERO, Money.ZERO)
        for (movement in movements) {
            if (movement.isCountable(user.settings.subcategoriesNotCountable)) {
                if (movement.isIncome()) {
                    totals.income = totals.income.add(movement.amount)
                } else {
                    totals.expense = totals.expense.add(movement.amount)
                }
                totals.saved = totals.saved.add(movement.amount)
            }
        }
        return totals
    }

    private fun calculateExpensesBy(
        filterPredicate: (Movement) -> Boolean,
        keySelector: (Movement) -> Category
    ): Map<Category, Money> {
        val distinctCategories = movements.filter(filterPredicate).map(keySelector).distinct()
        val categoriesMap = mutableMapOf<Category, Money>()
        for (distinctCategory in distinctCategories) {
            val total = movements.filter {
                distinctCategory == keySelector(it)
                        && filterPredicate(it)
            }.map { it.amount }.fold(Money.ZERO) { acc, amount -> acc.add(amount) }
            categoriesMap[distinctCategory] = total
        }
        return categoriesMap
    }

    private fun calculateExpensesByCategory(): Map<Category, Money> {
        return calculateExpensesBy(
            { it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense() },
            { it.subcategory.parentCategory }
        )
    }

    private fun calculateExpensesBySubCategory(): Map<Subcategory, Money> {
        return calculateExpensesBy(
            { it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense() },
            { it.subcategory }
        ).mapKeys { it.key as Subcategory }
    }

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
         * If the day of month of the localDate passed as parameter is less or equal dan 15 return true.
         */
        private fun isFirstHalfOfMonth(localDate: LocalDate) = localDate.dayOfMonth <= 15
    }
}