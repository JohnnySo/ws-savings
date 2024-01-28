package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import org.soneira.savings.domain.vo.id.MovementId
import org.springframework.data.mongodb.core.index.TextIndexed
import java.time.LocalDate

data class Movement(
    val operationDate: LocalDate,
    @TextIndexed val description: String,
    val amount: Money,
    val order: Order,
    val subcategory: Subcategory,
    @TextIndexed var comment: String,
    val balance: Money
) {
    var id: MovementId = MovementId("")

    constructor(
        id: MovementId, operationDate: LocalDate, description: String, amount: Money, order: Order,
        subcategory: Subcategory, comment: String, balance: Money
    ) :
            this(operationDate, description, amount, order, subcategory, comment, balance) {
        this.id = id
    }

    /**
     * Check if operationDate is between start and end
     * @param start the start date to check if it is between
     * @param end the end date to check if it is between
     * @return true if operationDate is between start and end
     */
    fun isDateBetween(start: LocalDate, end: LocalDate): Boolean {
        return operationDate in start..end
    }

    /**
     * @return true if the amount is negative
     */
    fun isExpense(): Boolean {
        return !amount.isGreaterThanZero
    }

    /**
     * @return true if the amount is positive
     */
    fun isIncome(): Boolean {
        return amount.isGreaterThanZero
    }

    /**
     * @return true if a movement should be taken into account to calculate totals
     */
    fun isCountable(subcategoriesNotCountable: List<Subcategory>): Boolean {
        return subcategory !in subcategoriesNotCountable
    }

    companion object {
        val dateAndOrderComparator = Comparator { m1: Movement, m2: Movement ->
            if (m1.operationDate == m2.operationDate) {
                m1.order.value.compareTo(m2.order.value)
            } else {
                m1.operationDate.compareTo(m2.operationDate)
            }
        }
    }
}