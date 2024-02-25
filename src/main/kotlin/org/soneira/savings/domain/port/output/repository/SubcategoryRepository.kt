package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.Subcategory
import java.math.BigInteger

interface SubcategoryRepository {
    /**
     * Get all the subcategories
     * @return the list of all subcategories [Subcategory]
     */
    fun getAll(): List<Subcategory>

    /**
     * Get one subcategory by its identifier or, if not exist, return the default category
     * @param id identifier of subcategory
     * @return the subcategory that fits the identifier or the default one [Subcategory]
     */
    fun getByIdOrDefault(id: BigInteger): Subcategory
}