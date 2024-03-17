package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Total

interface IndicatorRepository {
    /**
     * Gets the information of income, expense and total saved by year
     *
     * @param user  the user to filter data
     * @param years the list of years you want to get information
     * @return the list of [Total] of each year
     */
    fun getAnnualSummary(user: User, years: List<Int>): List<Total>

    /**
     * Gets all the expenses divided by category of the years passes as parameters
     *
     * @param user  the user to filter data
     * @param @param years the list of years you want to get information
     * @return List of [ExpensesByYear] with all the expenses by category
     */
    fun getExpensesByCategory(user: User, years: List<Int>): List<ExpensesByYear>

    /**
     * Gets all the expenses divided by subcategory of the years passes as parameters
     *
     * @param user  the user to filter data
     * @param @param years the list of years you want to get information
     * @return List of [ExpensesByYear] with all the expenses by subcategory
     */
    fun getExpensesBySubcategory(user: User, years: List<Int>): List<ExpensesByYear>
}