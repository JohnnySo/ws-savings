package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.vo.SortDirection
import org.soneira.savings.domain.model.vo.id.PeriodId
import org.springframework.data.domain.Page

interface GetPeriodUseCase {
    /**
     * Gets the periods paginated
     *
     * @param limit         indicates de maximum number of items to get for every page
     * @param offset        indicates how many elements should be omitted from the beginning
     * @param sortBy        the property you want to sort by
     * @param sortDirection the direction of the sort
     * @return A list of periods
     */
    fun getPaginatedPeriods(limit: Int, offset: Int, sortBy: String, sortDirection: SortDirection): Page<EconomicPeriod>

    /**
     * Get the details of a specific period
     *
     * @param id Identifier of saving period
     * @return details of a period
     */
    fun getPeriodById(id: PeriodId): EconomicPeriod
}