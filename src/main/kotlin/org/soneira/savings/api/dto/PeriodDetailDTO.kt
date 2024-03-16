package org.soneira.savings.api.dto

import java.time.LocalDate

data class PeriodDetailDTO(
    val id: String,
    val month: String,
    val year: Int,
    val start: LocalDate,
    val end: LocalDate,
    val totals: TotalsDTO,
    val expensesByCategory: List<ExpensesByCategoryDTO>,
    val expensesBySubcategory: List<ExpensesBySubcategoryDTO>,
    val movements: List<MovementViewDTO>
)