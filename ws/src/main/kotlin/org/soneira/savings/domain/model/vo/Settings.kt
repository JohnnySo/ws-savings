package org.soneira.savings.domain.model.vo

import org.soneira.savings.domain.model.entity.Subcategory

data class Settings(
    val periodStrategyType: PeriodStrategyType,
    val periodDefiner: Subcategory,
    val monthStartBoundary: Int,
    val monthEndBoundary: Int,
    val subcategoriesNotCountable: List<Subcategory>,
    val fileParserSettings: FileParserSettings
)