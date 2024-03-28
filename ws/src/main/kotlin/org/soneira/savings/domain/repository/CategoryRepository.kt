package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.model.vo.id.CategoryId


interface CategoryRepository {
    /**
     * Get all the categories
     * @return the list of all categories [Category]
     */
    fun getAll(): List<Category>

    /**
     * Get one category
     * @param id the category identifier [CategoryId]
     * @return the category [Category]
     */
    fun getById(id: CategoryId): Category

}