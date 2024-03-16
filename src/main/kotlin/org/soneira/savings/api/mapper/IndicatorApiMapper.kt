package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.ExpensesByCategoryDTO
import org.soneira.savings.api.dto.ExpensesBySubcategoryDTO
import org.soneira.savings.api.dto.ExpensesByYearDTO
import org.soneira.savings.api.dto.TotalsDTO
import org.soneira.savings.domain.model.vo.ExpenseByCategory
import org.soneira.savings.domain.model.vo.ExpenseBySubcategory
import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Total
import org.springframework.stereotype.Component

@Component
class IndicatorApiMapper(val categoryApiMapper: CategoryApiMapper) {
    fun asTotalsDTO(total: Total): TotalsDTO {
        val totalsDto = TotalsDTO(total.expense.amount, total.income.amount, total.saved.amount)
        totalsDto.year = total.year
        return totalsDto
    }

    fun asExpenseByYearAndCategoryDTO(expenses: ExpensesByYear): ExpensesByYearDTO {
        val expenseByYearDto = ExpensesByYearDTO(expenses.year)
        expenseByYearDto.expensesByCategoryDTO = expenses.expensesByCategory?.map { asExpensesByCategoryDTO(it) }
        return expenseByYearDto
    }

    fun asExpenseByYearAndSubcategoryDTO(expenses: ExpensesByYear): ExpensesByYearDTO {
        val expenseByYearDto = ExpensesByYearDTO(expenses.year)
        expenseByYearDto.expensesBySubcategoryDTO =
            expenses.expensesBySubcategory?.map { asExpensesBySubcategoryDTO(it) }
        return expenseByYearDto
    }

    fun asExpensesByCategoryDTO(expense: ExpenseByCategory): ExpensesByCategoryDTO {
        val categoryDto = categoryApiMapper.asCategoryDTO(expense.category)
        return ExpensesByCategoryDTO(categoryDto, expense.amount.amount)
    }

    fun asExpensesBySubcategoryDTO(expense: ExpenseBySubcategory): ExpensesBySubcategoryDTO {
        val subcategoryDto = categoryApiMapper.asSubcategoryDTO(expense.subcategory)
        return ExpensesBySubcategoryDTO(subcategoryDto, expense.amount.amount)
    }
}