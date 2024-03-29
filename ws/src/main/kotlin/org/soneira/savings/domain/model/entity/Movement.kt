package org.soneira.savings.domain.model.entity

import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Order
import org.soneira.savings.domain.model.vo.id.MovementId
import java.time.LocalDate

data class Movement(
    val operationDate: LocalDate,
    val description: String,
    val amount: Money,
    val order: Order,
    val subcategory: Subcategory,
    var comment: String,
    val balance: Money
) {
    lateinit var id: MovementId

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
     * @return true if a movement should be taken into account to calculate total
     */
    fun isCountable(subcategoriesNotCountable: List<Subcategory>): Boolean {
        return subcategory.id.value !in subcategoriesNotCountable.map { it.id.value }
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

    /**
     * Check if the lateinit prop id is initialized
     * @return true if it is initialized
     */
    fun isIdInit(): Boolean {
        return ::id.isInitialized
    }
}