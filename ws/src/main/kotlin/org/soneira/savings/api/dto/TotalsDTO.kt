package org.soneira.savings.api.dto

import java.math.BigDecimal

data class TotalsDTO(var income: BigDecimal, var expense: BigDecimal, var saved: BigDecimal) {
    var year: Int? = null
}