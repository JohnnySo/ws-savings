package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.Category


interface CategoryRepository {
    /**
     * Get all the categories
     * @return the list of all categories [Category]
     */
    fun getAll(): List<Category>
}