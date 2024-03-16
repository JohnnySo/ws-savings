package org.soneira.savings.infrastructure.persistence.mongo.document

import org.soneira.savings.domain.model.vo.PeriodStrategyType
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.INT32
import org.springframework.data.mongodb.core.mapping.FieldType.INT64
import java.math.BigInteger

data class SettingsDocument(
    val periodStrategyType: PeriodStrategyType,
    @Field(targetType = INT64)
    val periodDefiner: BigInteger,
    @Field(targetType = INT32)
    val monthStartBoundary: Int,
    @Field(targetType = INT32)
    val monthEndBoundary: Int,
    @Field(targetType = INT64)
    val subcategoriesNotCountable: List<BigInteger>,
    val fileParserSettings: FileParserSettingsDocument
)