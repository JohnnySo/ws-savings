package org.soneira.savings.domain.usecase

interface GetMasterDataUseCase {
    /**
     * Get all the different years stored in the periods
     *
     * @return the list of years as list of [Int]
     */
    fun getYears(): List<Int>
}