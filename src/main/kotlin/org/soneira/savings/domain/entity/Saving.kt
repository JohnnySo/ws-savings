package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.AggregateRoot
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Period
import org.soneira.savings.domain.vo.Totals
import org.soneira.savings.domain.vo.id.SavingId

data class Saving(
    val user: User,
    val period: Period,
    val filename: String,
    val movements: List<Movement>,
) : AggregateRoot<SavingId>() {
    private val totals: Totals
    private val expenseByCategory: Map<Category, Money>
    private val expenseBySubcategory: Map<Subcategory, Money>

    init {
        restoreOrderMovements()
        totals = calculateTotals()
        expenseByCategory = calculateExpensesByCategory()
        expenseBySubcategory = calculateExpensesBySubCategory()
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
}