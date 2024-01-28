package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.port.input.GetPeriodApplicationService
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.vo.SortDirection
import org.soneira.savings.domain.vo.id.PeriodId
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class GetPeriodApplicationServiceImpl(
    val periodRepository: PeriodRepository,
    val userRepository: UserRepository,
) : GetPeriodApplicationService {
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