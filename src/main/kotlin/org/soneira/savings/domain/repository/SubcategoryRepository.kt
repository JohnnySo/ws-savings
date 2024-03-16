package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.Subcategory
import java.math.BigInteger

interface SubcategoryRepository {
    /**
     * Get all the subcategories
     * @return the list of all subcategories [Subcategory]
     */
    fun getAll(): List<Subcategory>

    /**
     * Get one subcategory
     * @param id the subcategory identifier
     * @return the subcategory [Subcategory]
     */
    fun getById(id: BigInteger): Subcategory

}