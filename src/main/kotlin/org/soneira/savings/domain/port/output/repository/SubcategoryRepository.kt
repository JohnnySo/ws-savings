package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.Subcategory

interface SubcategoryRepository {
    /**
     * Get all the subcategories
     * @return the list of all subcategories [Subcategory]
     */
    fun getAll(): List<Subcategory>
}