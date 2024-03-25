package org.soneira.savings.domain.model.entity

import org.soneira.savings.domain.model.exception.DomainException
import org.soneira.savings.domain.model.vo.ExpenseByCategory
import org.soneira.savings.domain.model.vo.ExpenseBySubcategory
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Totals
import org.soneira.savings.domain.model.vo.id.PeriodId
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
    lateinit var totals: Totals
    lateinit var expenseByCategory: List<ExpenseByCategory>
    lateinit var expenseBySubcategory: List<ExpenseBySubcategory>
    lateinit var yearMonth: YearMonth

    constructor(
        id: PeriodId, user: User, start: LocalDate, end: LocalDate, filename: String, movements: List<Movement>,
        totals: Totals, expenseByCategory: List<ExpenseByCategory>, expenseBySubcategory: List<ExpenseBySubcategory>,
        yearMonth: YearMonth
    ) : this(user, start, end, filename, movements) {
        this.id = id
        this.totals = totals
        this.expenseByCategory = expenseByCategory
        this.expenseBySubcategory = expenseBySubcategory
        this.yearMonth = yearMonth
    }

    fun init() {
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
     * Check if the lateinit prop id is initialized
     * @return true if it is initialized
     */
    fun isIdInitialized(): Boolean {
        return ::id.isInitialized
    }

    /**
     * Reset the order of all the movements of the period to start in 0
     */
    private fun restoreOrderMovements() {
        movements.minByOrNull { it.order }?.let { min ->
            if (min.order.value > 0) {
                val minOrder = min.order.value
                movements.forEach { m -> m.order.value -= minOrder }
            } else {
                var minOrder = min.order.value
                movements.forEach { m ->
                    m.order.value = minOrder
                    minOrder++
                }
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
     * Sum all the expenses of every category.
     * @return a map that contains the category as key and the total amount expended as value
     */
    private fun calculateExpensesByCategory(): List<ExpenseByCategory> {
        val distinctCategories = movements
            .filter { it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense() }
            .map { it.subcategory.category }.distinct()
        val expensesByCategory = mutableListOf<ExpenseByCategory>()
        for (distinctCategory in distinctCategories) {
            val total = movements.filter {
                distinctCategory == it.subcategory.category
                        && it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense()
            }.map { it.amount }.fold(Money.ZERO) { acc, amount -> acc.add(amount) }
            expensesByCategory.add(ExpenseByCategory(distinctCategory, total))
        }
        return expensesByCategory
    }

    /**
     * Sum all the expenses of every subcategory.
     * @return a map that contains the subcategory as key and the total amount expended as value
     */
    private fun calculateExpensesBySubCategory(): List<ExpenseBySubcategory> {
        val distinctSubCategories = movements
            .filter { it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense() }
            .map { it.subcategory }.distinct()
        val expensesBySubcategory = mutableListOf<ExpenseBySubcategory>()
        for (distinctSubcategory in distinctSubCategories) {
            val total = movements.filter {
                distinctSubcategory == it.subcategory
                        && it.isCountable(user.settings.subcategoriesNotCountable) && it.isExpense()
            }.map { it.amount }.fold(Money.ZERO) { acc, amount -> acc.add(amount) }
            expensesBySubcategory.add(ExpenseBySubcategory(distinctSubcategory, total))
        }
        return expensesBySubcategory
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