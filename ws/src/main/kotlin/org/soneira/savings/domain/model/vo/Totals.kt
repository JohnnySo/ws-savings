package org.soneira.savings.domain.model.vo

data class Totals(var income: Money, var expense: Money, var saved: Money) {
    var year: Int? = null
}