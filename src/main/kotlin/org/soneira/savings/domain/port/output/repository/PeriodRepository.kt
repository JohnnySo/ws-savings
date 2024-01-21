package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.exception.ResourceNotFoundException
import org.soneira.savings.domain.vo.SortDirection
import org.soneira.savings.domain.vo.id.PeriodId
import org.soneira.savings.domain.vo.id.UserId
import org.springframework.data.domain.Page
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

    /**
     * Gets the periods paginated
     *
     * @param userId        the owner of the periods
     * @param limit         indicates de maximum number of items to get for every page
     * @param offset        indicates how many elements should be omitted from the beginning
     * @param sortBy        the property you want to sort by
     * @param sortDirection the direction of the sort
     * @return A list of periods
     */
    fun getPeriods(
        userId: UserId,
        limit: Int,
        offset: Int,
        sortBy: String,
        sortDirection: SortDirection
    ): Page<EconomicPeriod>

    /**
     * Get a specific period
     *
     * @param userId the owner of the periods
     * @param id     identifier of the period
     * @return       the requested period or [ResourceNotFoundException]
     */
    fun getPeriod(userId: UserId, id: PeriodId): EconomicPeriod
}