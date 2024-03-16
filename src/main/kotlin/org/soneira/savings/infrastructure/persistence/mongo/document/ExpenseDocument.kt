package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128
import org.springframework.data.mongodb.core.mapping.FieldType.INT64
import java.math.BigDecimal
import java.math.BigInteger

class ExpenseDocument(
    @Field(targetType = INT64)
    val key: BigInteger,
    @Field(targetType = DECIMAL128)
    val value: BigDecimal
)