package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.soneira.savings.domain.entity.Category
import org.soneira.savings.infrastructure.persistence.mongo.document.CategoryDocument

@Mapper
interface CategoryMapper {
    @Mapping(expression = "java(new CategoryId(categoryDocument.getId()))", target = "id")
    fun toDomain(categoryDocument: CategoryDocument): Category
}