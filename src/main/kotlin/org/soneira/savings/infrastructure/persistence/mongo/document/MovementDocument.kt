package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.math.BigDecimal
import java.time.LocalDate

data class MovementDocument(
    val operationDate: LocalDate,
    val description: String,
    @Field(targetType = FieldType.DECIMAL128) val amount: BigDecimal,
    val order: Int,
    val subcategory: String?,
    var comment: String?,
    @Field(targetType = FieldType.DECIMAL128) val balance: BigDecimal?
) {
    @Id
    var id: String = ""

    constructor(
        id: String, operationDate: LocalDate, description: String, amount: BigDecimal, order: Int,
        subcategory: String?, comment: String?, balance: BigDecimal?
    ) :
            this(operationDate, description, amount, order, subcategory, comment, balance) {
        this.id = id
    }
}