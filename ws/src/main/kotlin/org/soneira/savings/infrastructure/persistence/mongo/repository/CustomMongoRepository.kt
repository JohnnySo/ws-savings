package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.ExpensesProjection
import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.TotalsProjection

interface CustomMongoRepository {

    /**
     * Search movements in the collection of movements by description or comment. Partial match search
     * with the full text of every field
     * @param user        the user to look for the movements
     * @param searchParam the term to look for
     * @return the list of [MovementDocument] that has partially coincidence with at least one of the fields
     */
    fun searchMovements(user: String, searchParam: String): List<MovementDocument>

    /**
     * Get the sum of income, expense and totalReturn of each year passed as parameter
     *
     * @param user  the user to filter periods
     * @param years The list of years {@link List<Integer>}
     * @return list of [TotalsProjection]
     */
    fun getTotalsByYear(user: String, years: List<Int>): List<TotalsProjection>

    /**
     * Get the sum os expenses by category of the years passed as parameter
     *
     * @param user           the user to filter periods
     * @param years          the years of the total to calculate {@link List<Integer>}
     * @param useSubcategory boolean param that filter by subcategory and not by category
     * @return list of [ExpensesProjection]
     */
    fun getExpensesByYear(user: String, years: List<Int>, useSubcategory: Boolean): List<ExpensesProjection>

    /**
     * Get all the different years stored in economicPeriod.year
     *
     * @param user the user to filter periods
     * @return All the different years as list of [Integer]
     */
    fun findDistinctYears(user: String): List<Int>
}