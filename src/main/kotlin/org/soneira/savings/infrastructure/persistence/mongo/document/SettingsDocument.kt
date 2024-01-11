package org.soneira.savings.infrastructure.persistence.mongo.document

import org.soneira.savings.domain.vo.PeriodStrategyType

data class SettingsDocument(
    val periodStrategyType: PeriodStrategyType,
    val periodDefiner: Int,
    val monthStartBoundary: Int,
    val monthEndBoundary: Int,
    val subcategoriesNotCountable: List<Int>,
    val fileParserSettings: FileParserSettingsDocument
)