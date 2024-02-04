package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

@Document("movements")
data class MovementDocument(
    val operationDate: LocalDate,
    @TextIndexed var description: String,
    @Field(targetType = FieldType.DECIMAL128) val amount: BigDecimal,
    var order: Int,
    @Field(targetType = FieldType.INT64)
    var subcategory: BigInteger,
    @TextIndexed var comment: String,
    @Field(targetType = FieldType.DECIMAL128) val balance: BigDecimal,
    val user: String
) {
    @Id
    lateinit var id: String
    lateinit var periodId: String

    constructor(
        id: String, periodId: String, operationDate: LocalDate, description: String, amount: BigDecimal, order: Int,
        subcategory: BigInteger, comment: String, balance: BigDecimal, user: String
    ) :
            this(operationDate, description, amount, order, subcategory, comment, balance, user) {
        this.id = id
        this.periodId = periodId
    }
}