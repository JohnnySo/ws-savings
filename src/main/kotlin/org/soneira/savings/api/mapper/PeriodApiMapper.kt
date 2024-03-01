package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.CategoryDTO
import org.soneira.savings.api.dto.PeriodDTO
import org.soneira.savings.api.dto.PeriodDetailDTO
import org.soneira.savings.api.dto.SubcategoryDTO
import org.soneira.savings.api.dto.TotalsDTO
import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Totals
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.format.DateTimeFormatter

@Component
class PeriodApiMapper(val movementApiMapper: MovementApiMapper) {

    fun toDto(paginatedPeriods: Page<EconomicPeriod>): Page<PeriodDTO> {
        return PageImpl(
            paginatedPeriods.content.map { toDto(it) },
            paginatedPeriods.pageable,
            paginatedPeriods.totalElements
        )
    }

    fun toPeriodDetailDto(period: EconomicPeriod): PeriodDetailDTO {
        val month = period.yearMonth.format(DateTimeFormatter.ofPattern("MMMM", LocaleContextHolder.getLocale()))
        return PeriodDetailDTO(period.id.value,
            month,
            period.yearMonth.year,
            period.start,
            period.end,
            toTotalsDto(period.totals),
            toExpensesByCategoryDto(period.expenseByCategory),
            toExpensesBySubcategoryDto(period.expenseBySubcategory),
            period.movements.map { movementApiMapper.asMovementDTO(it) })
    }

    private fun toDto(period: EconomicPeriod): PeriodDTO {
        val month = period.yearMonth.format(DateTimeFormatter.ofPattern("MMMM", LocaleContextHolder.getLocale()))
        return PeriodDTO(
            period.id.value,
            month,
            period.yearMonth.year,
            period.start,
            period.end,
            toTotalsDto(period.totals)
        )
    }

    private fun toTotalsDto(totals: Totals): TotalsDTO {
        return TotalsDTO(totals.income.amount, totals.expense.amount, totals.saved.amount)
    }

    private fun toExpensesByCategoryDto(expenseByCategory: Map<Category, Money>): Map<CategoryDTO, BigDecimal> {
        return expenseByCategory.map { (key, value) ->
            CategoryDTO(
                key.id.value,
                key.descriptionEs
            ) to value.amount
        }.toMap()
    }

    private fun toExpensesBySubcategoryDto(expenseBySubcategory: Map<Subcategory, Money>): Map<SubcategoryDTO, BigDecimal> {
        return expenseBySubcategory.map { (key, value) ->
            SubcategoryDTO(
                key.id.value,
                key.descriptionEs
            ) to value.amount
        }.toMap()
    }
}