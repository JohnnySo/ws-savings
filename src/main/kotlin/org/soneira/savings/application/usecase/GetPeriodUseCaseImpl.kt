package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.vo.SortDirection
import org.soneira.savings.domain.model.vo.id.PeriodId
import org.soneira.savings.domain.repository.PeriodRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.usecase.GetPeriodUseCase
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class GetPeriodUseCaseImpl(
    val periodRepository: PeriodRepository,
    val userRepository: UserRepository,
) : GetPeriodUseCase {
    override fun getPaginatedPeriods(
        limit: Int,
        offset: Int,
        sortBy: String,
        sortDirection: SortDirection
    ): Page<EconomicPeriod> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return periodRepository.getPeriods(user, limit, offset, sortBy, sortDirection)
    }

    override fun getPeriodById(id: PeriodId): EconomicPeriod {
        val user = userRepository.getUser("john.doe@gmail.com")
        return periodRepository.getPeriod(user, id)
    }
}