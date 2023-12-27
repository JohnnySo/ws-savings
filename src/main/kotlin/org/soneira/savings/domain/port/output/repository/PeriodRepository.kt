package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.EconomicPeriod
import java.util.*

interface PeriodRepository {

    /**
     * Find the last period in the database
     *
     * @return the [EconomicPeriod] wrapped in [Optional] because could not have last period
     */
    fun findLastPeriod(): Optional<EconomicPeriod>
}