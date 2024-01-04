package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.BaseEntity
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
) : BaseEntity<PreMovementId>() {
}