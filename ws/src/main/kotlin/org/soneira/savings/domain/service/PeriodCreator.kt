package org.soneira.savings.domain.service

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.event.PeriodCreatedEvent
import java.util.*

interface PeriodCreator {

    /**
     * Divides the list of movements into periods [EconomicPeriod].
     * It calculates the total income, expense and saved of each period.
     * The list must be ordered by operationDate
     * @param user the user,
     * @param file the file stored in database
     * @param optLastPeriod the last period to complete it
     * @return All the movements divided into economic periods and with the total calculated
     */
    fun create(
        user: User,
        file: File,
        optLastPeriod: Optional<EconomicPeriod>
    ): PeriodCreatedEvent
}