package org.soneira.savings.domain.model.vo

data class ExpensesByYear(val year: Int) {
    var expensesByCategory: List<ExpenseByCategory>? = null
    var expensesBySubcategory: List<ExpenseBySubcategory>? = null
}