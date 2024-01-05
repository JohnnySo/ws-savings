package org.soneira.savings.domain.vo

import org.soneira.savings.domain.entity.Subcategory

data class Settings(
    val periodStrategyType: PeriodStrategyType,
    val periodDefiner: Subcategory,
    val monthStartBoundary: Int,
    val monthEndBoundary: Int,
    val subcategoriesNotCountable: List<Subcategory>,
    val fileParserSettings: FileParserSettings
)