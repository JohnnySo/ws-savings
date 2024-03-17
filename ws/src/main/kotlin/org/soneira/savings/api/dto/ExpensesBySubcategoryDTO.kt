package org.soneira.savings.api.dto

import java.math.BigDecimal

data class ExpensesBySubcategoryDTO(val subcategory: SubcategoryDTO, val amount: BigDecimal)