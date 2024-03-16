package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("periods")
data class EconomicPeriodDocument(
    val user: String,
    val start: LocalDate,
    val end: LocalDate,
    val filename: String,
    val totals: TotalDocument,
    val expenseByCategory: List<ExpenseDocument>,
    val expenseBySubcategory: List<ExpenseDocument>,
    val month: Int,
    val year: Int
) {
    @Id
    lateinit var id: String

    @Transient
    lateinit var movements: List<MovementDocument>

    constructor(
        id: String,
        user: String,
        start: LocalDate,
        end: LocalDate,
        filename: String,
        totals: TotalDocument,
        expenseByCategory: List<ExpenseDocument>,
        expenseBySubcategory: List<ExpenseDocument>,
        month: Int,
        year: Int
    ) : this(user, start, end, filename, totals, expenseByCategory, expenseBySubcategory, month, year) {
        this.id = id
    }
}