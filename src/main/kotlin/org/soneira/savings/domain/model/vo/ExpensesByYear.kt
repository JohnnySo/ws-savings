package org.soneira.savings.domain.model.vo

data class ExpensesByYear(val year: Int) {
    var expensesByCategory: List<ExpensesByCategory>? = null
    var expensesBySubcategory: List<ExpensesBySubcategory>? = null
}