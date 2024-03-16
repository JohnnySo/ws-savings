package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.vo.ExpensesByYear
import org.soneira.savings.domain.model.vo.Total

interface GetIndicatorUseCase {
    /**
     * Gets the information of income, expense and total saved by year
     *
     * @param years the list of years you want to get information
     * @return the list of [Total] of each year
     */
    fun getAnnualSummary(years: List<Int>): List<Total>

    /**
     * Gets all the expenses divided by category of the years passes as parameters
     *
     * @param @param years the list of years you want to get information
     * @return List of [ExpensesByYear] with all the expenses by category
     */
    fun getExpensesByCategory(years: List<Int>): List<ExpensesByYear>

    /**
     * Gets all the expenses divided by subcategory of the years passes as parameters
     *
     * @param @param years the list of years you want to get information
     * @return List of [ExpensesByYear] with all the expenses by subcategory
     */
    fun getExpensesBySubcategory(years: List<Int>): List<ExpensesByYear>
}