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
) : AggregateRoot() {
    lateinit var id: PeriodId
    var totals: Totals
    var expenseByCategory: Map<Category, Money>
    var expenseBySubcategory: Map<Subcategory, Money>
    var yearMonth: YearMonth

    constructor(
        id: PeriodId, user: User, start: LocalDate, end: LocalDate, filename: String, movements: List<Movement>,
        totals: Totals, expenseByCategory: Map<Category, Money>, expenseBySubcategory: Map<Subcategory, Money>,
        yearMonth: YearMonth
    ) : this(user, start, end, filename, movements) {
        this.id = id
        this.totals = totals
        this.expenseByCategory = expenseByCategory
        this.expenseBySubcategory = expenseBySubcategory
        this.yearMonth = yearMonth
    }

    init {
        restoreOrderMovements()
        totals = calculateTotals()
        expenseByCategory = calculateExpensesByCategory()
        expenseBySubcategory = calculateExpensesBySubCategory()
        yearMonth = getLogicalPeriod()
    }

    /**
     * Get the max order value in all the movements list
     */
    fun getMaxOrder(): Int {
        val max = movements.maxByOrNull { m -> m.order }
        return max?.order?.value ?: 0
    }

    /**
     * Reset the order of all the movements of the period to start in 1
     */
    private fun restoreOrderMovements() {
        val min = movements.minByOrNull { it.order }
        if (min != null) {
            for (movement in movements) {
                movement.order.value -= min.order.value.minus(1)
            }
        }
    }

    /**
     * Sum up all the movements of the same type (income or expense)
     * and it calculates the total saved by summing or subtracting all the movements
     * @return and object that hold the total income, expense and saved [Totals]
     */
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

    /**
     * Sum all the expenses of every category
     * @return a map that contains the category as key and the total amount expended as value
     */
    private fun calculateExpensesByCategory(): Map<Category, Money> {
        val distinctCategories = movements
            .filter {
                it.subcategory != null && it.isCountable(user.settings.subcategoriesNotCountable)
                        && it.isExpense()
            }
            .map { it.subcategory?.category }.distinct()
        val categoriesMap = mutableMapOf<Category, Money>()
        for (distinctCategory in distinctCategories) {
            val total = movements.filter {
                distinctCategory == it.subcategory?.category
                        && it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense()
            }.map { it.amount }.fold(Money.ZERO) { acc, amount -> acc.add(amount) }
            if (distinctCategory != null) {
                categoriesMap[distinctCategory] = total
            }
        }
        return categoriesMap
    }

    /**
     * Sum all the expenses of every subcategory.
     * @return a map that contains the subcategory as key and the total amount expended as value
     */
    private fun calculateExpensesBySubCategory(): Map<Subcategory, Money> {
        val distinctSubCategories = movements
            .filter {
                it.subcategory != null && it.isCountable(user.settings.subcategoriesNotCountable)
                        && it.isExpense()
            }
            .map { it.subcategory }.distinct()
        val categoriesMap = mutableMapOf<Subcategory, Money>()
        for (distinctSubCategory in distinctSubCategories) {
            val total = movements.filter {
                distinctSubCategory == it.subcategory
                        && it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense()
            }.map { it.amount }.fold(Money.ZERO) { acc, amount -> acc.add(amount) }
            if (distinctSubCategory != null) {
                categoriesMap[distinctSubCategory] = total
            }
        }
        return categoriesMap.mapKeys { it.key }
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

    companion object {
        /**
         * If the day of month of the localDate passed as parameter is less or equal dan 15 return true.
         */
        private fun isFirstHalfOfMonth(localDate: LocalDate) = localDate.dayOfMonth <= 15
    }
}