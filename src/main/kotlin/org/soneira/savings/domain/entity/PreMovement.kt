package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import org.soneira.savings.domain.vo.id.PreMovementId
import java.time.LocalDate

data class PreMovement(
    val operationDate: LocalDate,
    val description: String,
    val amount: Money,
    val order: Order,
    val category: String?,
    val subcategory: String?,
    var comment: String?,
    val balance: Money?
) {
    lateinit var id: PreMovementId

    constructor(
        id: PreMovementId, operationDate: LocalDate, description: String, amount: Money, order: Order,
        category: String?, subcategory: String?, comment: String?, balance: Money?
    ) :
            this(operationDate, description, amount, order, category, subcategory, comment, balance) {
        this.id = id
    }
}