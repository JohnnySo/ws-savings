package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

@Document("periods")
data class EconomicPeriodDocument(
    val user: String,
    val start: LocalDate,
    val end: LocalDate,
    val filename: String,
    val totals: TotalsDocument,
    val expenseByCategory: Map<BigInteger, BigDecimal>,
    val expenseBySubcategory: Map<BigInteger, BigDecimal>,
    val month: Int,
    val year: Int,
    @Transient var movements: List<MovementDocument>,
) {
    @Id
    lateinit var id: String

    constructor(
        id: String,
        user: String,
        start: LocalDate,
        end: LocalDate,
        filename: String,
        totals: TotalsDocument,
        expenseByCategory: Map<BigInteger, BigDecimal>,
        expenseBySubcategory: Map<BigInteger, BigDecimal>,
        month: Int,
        year: Int,
        movements: List<MovementDocument>,
    ) : this(user, start, end, filename, totals, expenseByCategory, expenseBySubcategory, month, year, movements) {
        this.id = id
    }

}