package org.soneira.savings.api.dto

import java.math.BigDecimal
import java.time.LocalDate

data class MovementViewDTO(
    val operationDate: LocalDate,
    val description: String,
    val amount: BigDecimal,
    val category: CategoryDTO,
    val subcategory: SubcategoryDTO,
    val comment: String,
    val balance: BigDecimal,
    val order: Int
) {
    var id: String? = null
}