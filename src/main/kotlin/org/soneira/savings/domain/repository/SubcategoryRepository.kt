package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.Subcategory

interface SubcategoryRepository {
    /**
     * Get all the subcategories
     * @return the list of all subcategories [Subcategory]
     */
    fun getAll(): List<Subcategory>

}