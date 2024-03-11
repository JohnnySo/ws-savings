package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import java.math.BigDecimal

class TotalsProjection(
    @Id val year: Int, val totalSaved: BigDecimal,
    val totalIncome: BigDecimal, val totalExpense: BigDecimal
)