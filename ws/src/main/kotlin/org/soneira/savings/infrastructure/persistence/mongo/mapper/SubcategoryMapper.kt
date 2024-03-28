package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.vo.id.CategoryId
import org.soneira.savings.domain.model.vo.id.SubcategoryId
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.SubcategoryDocument
import org.springframework.stereotype.Component

@Component
class SubcategoryMapper(val categoryRepository: CategoryRepository) {
    fun asSubcategory(subcategoryDocument: SubcategoryDocument): Subcategory {
        return Subcategory(
            SubcategoryId(subcategoryDocument.id.toInt()),
            subcategoryDocument.key,
            subcategoryDocument.description,
            subcategoryDocument.descriptionEs,
            categoryRepository.getById(CategoryId(subcategoryDocument.category))
        )
    }
}