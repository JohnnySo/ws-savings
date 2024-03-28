package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.vo.ExpenseByCategory
import org.soneira.savings.domain.model.vo.ExpenseBySubcategory
import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Totals
import org.soneira.savings.domain.model.vo.id.CategoryId
import org.soneira.savings.domain.model.vo.id.SubcategoryId
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.ExpenseDocument
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

    fun asExpensesByCategory(expense: ExpenseDocument): ExpenseByCategory {
        return ExpenseByCategory(categoryRepository.getById(CategoryId( expense.key)), Money.of(expense.amount))
    }

    fun asExpensesBySubcategory(expense: ExpenseDocument): ExpenseBySubcategory {
        return ExpenseBySubcategory(subcategoryRepository.getById(SubcategoryId(expense.key)), Money.of(expense.amount))
    }
}