package org.soneira.savings.domain.model.vo

import org.soneira.savings.domain.model.entity.Category

data class ExpensesByCategory(val category: Category, val amount: Money)