package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.vo.ExpenseByCategory
import org.soneira.savings.domain.model.vo.ExpenseBySubcategory
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Totals
import org.soneira.savings.domain.model.vo.id.PeriodId
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.EconomicPeriodDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.ExpenseDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.TotalsDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class PeriodMapper(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val subcategoryRepository: SubcategoryRepository,
    private val movementMapper: MovementMapper
) {
    fun asPeriodDocument(period: EconomicPeriod): EconomicPeriodDocument {
        val periodDocument = EconomicPeriodDocument(
            period.user.id.value,
            period.start,
            period.end,
            period.filename,
            asTotalsDocument(period.totals),
            period.expenseByCategory.map { (key, value) -> ExpenseDocument(key.id.value, value.amount) },
            period.expenseBySubcategory.map { (key, value) -> ExpenseDocument(key.id.value, value.amount) },
            period.yearMonth.month.value,
            period.yearMonth.year
        )
        if (period.isIdInitialized()) {
            periodDocument.id = period.id.value
        }
        periodDocument.movements = period.movements.map { movementMapper.asMovementDocument(it, period.user.id.value) }
        return periodDocument
    }

    fun asPeriod(period: EconomicPeriodDocument): EconomicPeriod {
        return EconomicPeriod(
            PeriodId(period.id),
            userRepository.getUserById(period.user),
            period.start,
            period.end,
            period.filename,
            period.movements.map { movementMapper.asMovement(it) },
            asTotals(period.totals),
            asExpensesByCategoryDto(period.expenseByCategory),
            asExpensesBySubcategoryDto(period.expenseBySubcategory),
            YearMonth.of(period.year, period.month)
        )
    }

    fun asPageOfPeriods(paginatedPeriodDocuments: Page<EconomicPeriodDocument>): Page<EconomicPeriod> {
        return PageImpl(
            paginatedPeriodDocuments.content.map {
                it.movements = emptyList()
                asPeriod(it)
            },
            paginatedPeriodDocuments.pageable,
            paginatedPeriodDocuments.totalElements
        )
    }

    private fun asTotalsDocument(totals: Totals): TotalsDocument {
        return TotalsDocument(totals.income.amount, totals.expense.amount, totals.saved.amount)
    }

    private fun asTotals(totals: TotalsDocument): Totals {
        return Totals(Money.of(totals.income), Money.of(totals.expense), Money.of(totals.saved))
    }

    private fun asExpensesByCategoryDto(expensesByCategory: List<ExpenseDocument>): List<ExpenseByCategory> {
        return expensesByCategory.map {
            ExpenseByCategory(categoryRepository.getById(it.key), Money.of(it.value))
        }
    }

    private fun asExpensesBySubcategoryDto(expensesBySubcategory: List<ExpenseDocument>): List<ExpenseBySubcategory> {
        return expensesBySubcategory.map {
            ExpenseBySubcategory(subcategoryRepository.getById(it.key), Money.of(it.value))
        }
    }
}