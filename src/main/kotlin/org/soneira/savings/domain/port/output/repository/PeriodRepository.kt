package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.User
import java.util.*

interface PeriodRepository {

    /**
     * Find the last period in the database
     * @param user the user connected [User]
     * @return the [EconomicPeriod] wrapped in [Optional] because could not have last period
     */
    fun findLastPeriod(user: User): Optional<EconomicPeriod>

    /**
     * Find the last period in the database
     * @param economicPeriods the list of economical periods
     * @return the [EconomicPeriod] list with identifiers
     */
    fun save(economicPeriods: List<EconomicPeriod>): List<EconomicPeriod>
}