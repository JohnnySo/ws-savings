package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.User

interface MasterRepository {
    /**
     * Get all the different years stored in the periods
     *
     * @param user  the user to filter data
     * @return the list of years as list of [Int]
     */
    fun getYears(user: User): List<Int>
}