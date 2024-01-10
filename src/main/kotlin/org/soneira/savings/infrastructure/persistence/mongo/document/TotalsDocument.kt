package org.soneira.savings.infrastructure.persistence.mongo.document

import java.math.BigDecimal

data class TotalsDocument(var income: BigDecimal, var expense: BigDecimal, var saved: BigDecimal)