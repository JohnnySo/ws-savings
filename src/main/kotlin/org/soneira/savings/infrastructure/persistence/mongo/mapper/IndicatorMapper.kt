package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.vo.ExpensesByCategory
import org.soneira.savings.domain.model.vo.ExpensesBySubcategory
import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Totals
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.ExpenseProjection
import org.soneira.savings.infrastructure.persistence.mongo.document.ExpensesProjection
import org.soneira.savings.infrastructure.persistence.mongo.document.TotalsProjection
import org.springframework.stereotype.Component

@Component
class IndicatorMapper(
    private val categoryRepository: CategoryRepository,
    private val subcategoryRepository: SubcategoryRepository
) {
    fun asTotals(totalsProjection: TotalsProjection): Totals {
        val totals = Totals(
            Money.of(totalsProjection.totalExpense),
            Money.of(totalsProjection.totalIncome), Money.of(totalsProjection.totalSaved)
        )
        totals.year = totalsProjection.year
        return totals
    }

    fun asExpenseByYearAndCategory(expenses: ExpensesProjection): ExpensesByYear {
        val expenseByYear = ExpensesByYear(expenses.year)
        expenseByYear.expensesByCategory = expenses.expenses.map { asExpensesByCategory(it) }
        return expenseByYear
    }

    fun asExpenseByYearAndSubcategory(expenses: ExpensesProjection): ExpensesByYear {
        val expenseByYear = ExpensesByYear(expenses.year)
        expenseByYear.expensesBySubcategory = expenses.expenses.map { asExpensesBySubcategory(it) }
        return expenseByYear
    }

    fun asExpensesByCategory(expense: ExpenseProjection): ExpensesByCategory {
        val category = categoryRepository.getAll().first { expense.key == it.id.value }
        return ExpensesByCategory(category, Money.of(expense.amount))
    }

    fun asExpensesBySubcategory(expense: ExpenseProjection): ExpensesBySubcategory {
        val subcategory = subcategoryRepository.getAll().first { expense.key == it.id.value }
        return ExpensesBySubcategory(subcategory, Money.of(expense.amount))
    }
}