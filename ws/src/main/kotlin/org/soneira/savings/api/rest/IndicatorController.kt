package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.ExpensesByYearDTO
import org.soneira.savings.api.dto.IndicatorRequestDTO
import org.soneira.savings.api.dto.TotalsDTO
import org.soneira.savings.api.mapper.IndicatorApiMapper
import org.soneira.savings.domain.usecase.GetIndicatorUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/indicators")
class IndicatorController(
    val getIndicatorUseCase: GetIndicatorUseCase,
    val indicatorApiMapper: IndicatorApiMapper
) {

    @PostMapping("/annual-summary", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun annualSummary(@RequestBody(required = true) indicatorRequest: IndicatorRequestDTO):
            ResponseEntity<List<TotalsDTO>> {
        val totalsDto = getIndicatorUseCase.getAnnualSummary(indicatorRequest.years)
            .map { indicatorApiMapper.asTotalsDTO(it) }
        return ResponseEntity.ok(totalsDto)
    }

    @PostMapping("/category/annual-expenses", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun annualExpensesByCategory(@RequestBody(required = true) indicatorRequest: IndicatorRequestDTO):
            ResponseEntity<List<ExpensesByYearDTO>> {
        val expensesByCategory = getIndicatorUseCase.getExpensesByCategory(indicatorRequest.years)
            .map { indicatorApiMapper.asExpenseByYearAndCategoryDTO(it) }
        return ResponseEntity.ok(expensesByCategory)
    }

    @PostMapping("/subcategory/annual-expenses", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun annualExpensesBySubcategory(@RequestBody(required = true) indicatorRequest: IndicatorRequestDTO):
            ResponseEntity<List<ExpensesByYearDTO>> {
        val expensesBySubcategory = getIndicatorUseCase.getExpensesBySubcategory(indicatorRequest.years)
            .map { indicatorApiMapper.asExpenseByYearAndSubcategoryDTO(it) }
        return ResponseEntity.ok(expensesBySubcategory)
    }
}