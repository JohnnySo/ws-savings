package org.soneira.savings.api.dto

import java.math.BigDecimal
import java.time.LocalDate

data class MovementDTO(
    val id: String,
    val description: String,
    val subcategory: SubcategoryDTO,
    val comment: String,
    val order: Int,
) {
    lateinit var operationDate: LocalDate
    lateinit var amount: BigDecimal
    lateinit var category: CategoryDTO
    lateinit var balance: BigDecimal

    constructor(
        id: String, operationDate: LocalDate, description: String, amount: BigDecimal,
        category: CategoryDTO, subcategory: SubcategoryDTO, comment: String,
        balance: BigDecimal, order: Int
    ) :
            this(id, description, subcategory, comment, order) {
        this.operationDate = operationDate
        this.amount = amount
        this.category = category
        this.balance = balance

    }
}