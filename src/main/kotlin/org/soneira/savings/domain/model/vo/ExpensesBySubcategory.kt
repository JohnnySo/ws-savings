package org.soneira.savings.domain.model.vo

import org.soneira.savings.domain.model.entity.Subcategory

data class ExpensesBySubcategory(val subcategory: Subcategory, val amount: Money)