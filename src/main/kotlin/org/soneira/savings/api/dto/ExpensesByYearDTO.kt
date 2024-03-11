package org.soneira.savings.api.dto

data class ExpensesByYearDTO(val year: Int) {
    var expensesByCategoryDTO: List<ExpensesByCategoryDTO>? = null
    var expensesBySubcategoryDTO: List<ExpensesBySubcategoryDTO>? = null
}