package org.soneira.savings.domain.vo

import org.soneira.savings.domain.entity.Subcategory
import org.soneira.savings.domain.service.period.strategy.PeriodStrategyFactory

data class BusinessSettings(
    val periodStrategyType: PeriodStrategyFactory,
    val periodDefiner: Subcategory,
    val monthStartBoundary: Int,
    val monthEndBoundary: Int,
    val subcategoriesNotCountable: List<Subcategory>,
    val fileParserSettings: FileParserSettings
)