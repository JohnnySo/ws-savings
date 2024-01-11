package org.soneira.savings.infrastructure.persistence.mongo.document

import org.soneira.savings.domain.vo.PeriodStrategyType
import java.math.BigInteger

data class SettingsDocument(
    val periodStrategyType: PeriodStrategyType,
    val periodDefiner: BigInteger,
    val monthStartBoundary: Int,
    val monthEndBoundary: Int,
    val subcategoriesNotCountable: List<BigInteger>,
    val fileParserSettings: FileParserSettingsDocument
)