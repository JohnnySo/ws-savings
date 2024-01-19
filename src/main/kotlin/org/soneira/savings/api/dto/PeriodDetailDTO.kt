package org.soneira.savings.api.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PeriodDetailDTO(
    val id: String,
    val month: String,
    val year: Int,
    val start: LocalDate,
    val end: LocalDate,
    val totals: TotalsDTO,
    val expenseByCategory: Map<CategoryDTO, BigDecimal>,
    val expenseBySubcategory: Map<SubcategoryDTO, BigDecimal>,
    val movements: List<MovementDTO>
)