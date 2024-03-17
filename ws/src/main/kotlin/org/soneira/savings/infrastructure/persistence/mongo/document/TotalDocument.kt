package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128
import java.math.BigDecimal

data class TotalDocument(
    @Field(targetType = DECIMAL128)
    var income: BigDecimal,
    @Field(targetType = DECIMAL128)
    var expense: BigDecimal,
    @Field(targetType = DECIMAL128)
    var saved: BigDecimal
)