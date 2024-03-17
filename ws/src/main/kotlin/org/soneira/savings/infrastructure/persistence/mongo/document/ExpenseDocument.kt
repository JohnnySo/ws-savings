package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128
import org.springframework.data.mongodb.core.mapping.FieldType.INT32
import java.math.BigDecimal

class ExpenseDocument(
    @Field(targetType = INT32)
    val key: Int,
    @Field(targetType = DECIMAL128)
    val value: BigDecimal
)