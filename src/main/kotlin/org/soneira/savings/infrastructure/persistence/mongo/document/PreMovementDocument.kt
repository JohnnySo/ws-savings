package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.math.BigDecimal
import java.time.LocalDate

data class PreMovementDocument(
    val operationDate: LocalDate,
    val description: String,
    @Field(targetType = FieldType.DECIMAL128) val amount: BigDecimal,
    val order: Int,
    val subcategory: String?,
    var comment: String?,
    @Field(targetType = FieldType.DECIMAL128) val balance: BigDecimal?
)