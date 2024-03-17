package org.soneira.savings.domain.model.vo

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

data class Money(val amount: BigDecimal) {
    fun add(money: Money): Money {
        return of(setScale(amount.add(money.amount)))
    }

    val isGreaterThanZero: Boolean
        get() = amount > BigDecimal.ZERO

    override fun hashCode(): Int {
        return Objects.hash(this.amount)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false
        val money = other as Money
        return this.amount == money.amount
    }

    companion object {
        val ZERO = of(BigDecimal.ZERO)
        private fun setScale(input: BigDecimal): BigDecimal {
            return input.setScale(2, RoundingMode.HALF_EVEN)
        }

        fun of(value: BigDecimal): Money {
            return Money(setScale(value))
        }
    }
}