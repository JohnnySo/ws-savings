package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.port.output.repository.CategoryRepository
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Totals
import org.soneira.savings.domain.vo.id.PeriodId
import org.soneira.savings.infrastructure.persistence.mongo.document.EconomicPeriodDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.TotalsDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component
import java.time.YearMonth

@Component
class PeriodMapper(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubcategoryRepository,
    private val movementMapper: MovementMapper
) {
    fun toDocument(period: EconomicPeriod): EconomicPeriodDocument {
        val periodDocument = EconomicPeriodDocument(
            period.user.id.value,
            period.start,
            period.end,
            period.filename,
            toDocument(period.totals),
            period.expenseByCategory.map { (key, value) -> key.id.value to value.amount }.toMap(),
            period.expenseBySubcategory.map { (key, value) -> key.id.value to value.amount }.toMap(),
            period.yearMonth.month.value,
            period.yearMonth.year
        )
        if (period.isIdInitialized()) {
            periodDocument.id = period.id.value
        }
        periodDocument.movements = period.movements.map { movementMapper.toDocument(it) }
        return periodDocument
    }

    fun toDomain(period: EconomicPeriodDocument): EconomicPeriod {
        val categories = categoryRepository.getAll()
        val subcategories = subCategoryRepository.getAll()
        return EconomicPeriod(
            PeriodId(period.id),
            userRepository.getUserById(period.user),
            period.start,
            period.end,
            period.filename,
            period.movements.map { movementMapper.toDomain(it) },
            toDomain(period.totals),
            period.expenseByCategory.map { (key, value) -> categories.first { it.id.value == key } to Money.of(value) }
                .toMap(),
            period.expenseBySubcategory.map { (k, v) -> subcategories.first { it.id.value == k } to Money.of(v) }
                .toMap(),
            YearMonth.of(period.year, period.month)
        )
    }

    fun toDomain(paginatedPeriodDocuments: Page<EconomicPeriodDocument>): Page<EconomicPeriod> {
        return PageImpl(
            paginatedPeriodDocuments.content.map {
                it.movements = emptyList()
                toDomain(it)
            },
            paginatedPeriodDocuments.pageable,
            paginatedPeriodDocuments.totalElements
        )
    }

    private fun toDocument(totals: Totals): TotalsDocument {
        return TotalsDocument(totals.income.amount, totals.expense.amount, totals.saved.amount)
    }

    private fun toDomain(totals: TotalsDocument): Totals {
        return Totals(Money.of(totals.income), Money.of(totals.expense), Money.of(totals.saved))
    }
}