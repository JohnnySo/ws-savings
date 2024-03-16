package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.ExpensesByCategoryDTO
import org.soneira.savings.api.dto.ExpensesBySubcategoryDTO
import org.soneira.savings.api.dto.PeriodDTO
import org.soneira.savings.api.dto.PeriodDetailDTO
import org.soneira.savings.api.dto.TotalsDTO
import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.vo.ExpenseByCategory
import org.soneira.savings.domain.model.vo.ExpenseBySubcategory
import org.soneira.savings.domain.model.vo.Total
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class PeriodApiMapper(
    val movementApiMapper: MovementApiMapper,
    val categoryApiMapper: CategoryApiMapper
) {

    fun asPageOfPeriodDto(paginatedPeriods: Page<EconomicPeriod>): Page<PeriodDTO> {
        return PageImpl(
            paginatedPeriods.content.map { asPageOfPeriodDto(it) },
            paginatedPeriods.pageable,
            paginatedPeriods.totalElements
        )
    }

    fun asPeriodDetailDto(period: EconomicPeriod): PeriodDetailDTO {
        val month = period.yearMonth.format(DateTimeFormatter.ofPattern("MMMM", LocaleContextHolder.getLocale()))
        return PeriodDetailDTO(period.id.value,
            month,
            period.yearMonth.year,
            period.start,
            period.end,
            asTotalsDto(period.total),
            asExpensesByCategoryDto(period.expenseByCategory),
            asExpensesBySubcategoryDto(period.expenseBySubcategory),
            period.movements.map { movementApiMapper.asMovementViewDTO(it) })
    }

    private fun asPageOfPeriodDto(period: EconomicPeriod): PeriodDTO {
        val month = period.yearMonth.format(DateTimeFormatter.ofPattern("MMMM", LocaleContextHolder.getLocale()))
        return PeriodDTO(
            period.id.value,
            month,
            period.yearMonth.year,
            period.start,
            period.end,
            asTotalsDto(period.total)
        )
    }

    private fun asTotalsDto(total: Total): TotalsDTO {
        return TotalsDTO(total.income.amount, total.expense.amount, total.saved.amount)
    }

    private fun asExpensesByCategoryDto(expensesByCategory: List<ExpenseByCategory>): List<ExpensesByCategoryDTO> {
        return expensesByCategory.map {
            ExpensesByCategoryDTO(
                categoryApiMapper.asCategoryDTO(it.category),
                it.amount.amount
            )
        }
    }

    private fun asExpensesBySubcategoryDto(expenseBySubcategory: List<ExpenseBySubcategory>): List<ExpensesBySubcategoryDTO> {
        return expenseBySubcategory.map {
            ExpensesBySubcategoryDTO(
                categoryApiMapper.asSubcategoryDTO(it.subcategory),
                it.amount.amount
            )
        }
    }
}