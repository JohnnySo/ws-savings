package org.soneira.savings.infrastructure.persistence.mongo.document

import java.math.BigDecimal
import java.math.BigInteger

class ExpenseProjection(val key: BigInteger, val amount: BigDecimal)