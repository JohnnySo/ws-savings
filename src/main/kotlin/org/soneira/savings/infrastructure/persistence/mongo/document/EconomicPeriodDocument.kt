package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document("periods")
data class EconomicPeriodDocument(
    val user: String,
    val start: LocalDate,
    val end: LocalDate,
    val filename: String,
    val totals: TotalsDocument,
    val expenseByCategory: Map<Int, BigDecimal>,
    val expenseBySubcategory: Map<Int, BigDecimal>,
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
        expenseByCategory: Map<Int, BigDecimal>,
        expenseBySubcategory: Map<Int, BigDecimal>,
        month: Int,
        year: Int,
        movements: List<MovementDocument>,
    ) : this(user, start, end, filename, totals, expenseByCategory, expenseBySubcategory, month, year, movements) {
        this.id = id
    }

}