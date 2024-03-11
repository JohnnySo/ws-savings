package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Totals
import org.soneira.savings.domain.repository.IndicatorRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.IndicatorMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.CustomMongoRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class IndicatorRepositoryImpl(
    val customMongoRepository: CustomMongoRepository,
    val indicatorMapper: IndicatorMapper
) : IndicatorRepository {
    override fun getAnnualSummary(user: User, years: List<Int>): List<Totals> {
        return customMongoRepository.getTotalsByYear(user.id.value, years).map { indicatorMapper.asTotals(it) }
    }

    override fun getExpensesByCategory(user: User, years: List<Int>): List<ExpensesByYear> {
        return customMongoRepository.getExpensesByYear(user.id.value, years, false)
            .map { indicatorMapper.asExpenseByYearAndCategory(it) }
    }

    override fun getExpensesBySubcategory(user: User, years: List<Int>): List<ExpensesByYear> {
        return customMongoRepository.getExpensesByYear(user.id.value, years, true)
            .map { indicatorMapper.asExpenseByYearAndSubcategory(it) }
    }
}