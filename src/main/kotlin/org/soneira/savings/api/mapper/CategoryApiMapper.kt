package org.soneira.savings.api.mapper

import org.soneira.savings.api.dto.CategoryDTO
import org.soneira.savings.api.dto.SubcategoryDTO
import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.model.entity.Subcategory
import org.springframework.stereotype.Component

@Component
class CategoryApiMapper {
    fun asCategoryDTO(category: Category): CategoryDTO {
        return CategoryDTO(category.id.value, category.descriptionEs)
    }

    fun asSubcategoryDTO(subcategory: Subcategory): SubcategoryDTO {
        return SubcategoryDTO(subcategory.id.value, subcategory.descriptionEs)
    }
}