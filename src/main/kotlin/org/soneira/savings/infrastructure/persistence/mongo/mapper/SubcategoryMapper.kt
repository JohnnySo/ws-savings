package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.Subcategory
import org.soneira.savings.domain.port.output.repository.CategoryRepository
import org.soneira.savings.domain.vo.id.SubcategoryId
import org.soneira.savings.infrastructure.persistence.mongo.document.SubcategoryDocument
import org.springframework.stereotype.Component

@Component
class SubcategoryMapper(val categoryRepository: CategoryRepository) {
    fun toDomain(subcategoryDocument: SubcategoryDocument): Subcategory {
        val categories = categoryRepository.getAll()
        return Subcategory(
            SubcategoryId(subcategoryDocument.id),
            subcategoryDocument.key,
            subcategoryDocument.description,
            subcategoryDocument.descriptionEs,
            categories.first { subcategoryDocument.category == it.id.value }
        )
    }
}