package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.Category


interface CategoryRepository {
    /**
     * Get all the categories
     * @return the list of all categories [Category]
     */
    fun getAll(): List<Category>
}