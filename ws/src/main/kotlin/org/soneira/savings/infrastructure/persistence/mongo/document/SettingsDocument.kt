package org.soneira.savings.infrastructure.persistence.mongo.document

import org.soneira.savings.domain.model.vo.PeriodStrategyType
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.INT32

data class SettingsDocument(
    val periodStrategyType: PeriodStrategyType,
    @Field(targetType = INT32)
    val periodDefiner: Int,
    @Field(targetType = INT32)
    val monthStartBoundary: Int,
    @Field(targetType = INT32)
    val monthEndBoundary: Int,
    @Field(targetType = INT32)
    val subcategoriesNotCountable: List<Int>,
    val fileParserSettings: FileParserSettingsDocument
)