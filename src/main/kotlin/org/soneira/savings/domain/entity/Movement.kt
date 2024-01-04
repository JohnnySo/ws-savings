package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.BaseEntity
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import org.soneira.savings.domain.vo.id.MovementId
import java.time.LocalDate

data class Movement(
    val operationDate: LocalDate,
    val description: String,
    val amount: Money,
    val order: Order,
    val subcategory: Subcategory?,
    var comment: String?,
    val balance: Money?
) : BaseEntity<MovementId>() {
    fun isDateBetween(start: LocalDate, end: LocalDate?): Boolean {
        return if (end == null) {
            operationDate > start
        } else {
            operationDate in start..end
        }
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