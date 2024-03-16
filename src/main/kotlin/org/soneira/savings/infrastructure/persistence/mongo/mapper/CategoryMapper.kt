package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.model.vo.id.CategoryId
import org.soneira.savings.infrastructure.persistence.mongo.document.CategoryDocument
import org.springframework.stereotype.Component

@Component
class CategoryMapper {
    fun asCategory(categoryDocument: CategoryDocument): Category {
        return Category(
            CategoryId(categoryDocument.id), categoryDocument.key, categoryDocument.description,
            categoryDocument.descriptionEs, categoryDocument.typeId, categoryDocument.type
        )
    }
}