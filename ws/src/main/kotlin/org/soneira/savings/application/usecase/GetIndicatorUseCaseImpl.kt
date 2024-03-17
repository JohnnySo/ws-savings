package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Total
import org.soneira.savings.domain.repository.IndicatorRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.usecase.GetIndicatorUseCase
import org.springframework.stereotype.Service

@Service
class GetIndicatorUseCaseImpl(
    val indicatorRepository: IndicatorRepository,
    val userRepository: UserRepository
) : GetIndicatorUseCase {
    override fun getAnnualSummary(years: List<Int>): List<Total> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return indicatorRepository.getAnnualSummary(user, years)
    }

    override fun getExpensesByCategory(years: List<Int>): List<ExpensesByYear> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return indicatorRepository.getExpensesByCategory(user, years)
    }

    override fun getExpensesBySubcategory(years: List<Int>): List<ExpensesByYear> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return indicatorRepository.getExpensesBySubcategory(user, years)
    }
}