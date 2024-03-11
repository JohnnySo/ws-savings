package org.soneira.savings.api.dto

import java.math.BigDecimal

data class ExpensesByCategoryDTO(val category: CategoryDTO, val amount: BigDecimal)