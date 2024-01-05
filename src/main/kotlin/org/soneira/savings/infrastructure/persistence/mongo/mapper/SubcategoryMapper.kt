package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.soneira.savings.domain.entity.Category
import org.soneira.savings.domain.entity.Subcategory
import org.soneira.savings.infrastructure.persistence.mongo.document.SubcategoryDocument

@Mapper
interface SubcategoryMapper {
    @Mapping(expression = "java(new SubcategoryId(subcategoryDocument.getId()))", target = "id")
    @Mapping(expression = "java(parentCategory)", target = "parentCategory")
    fun toDomain(subcategoryDocument: SubcategoryDocument, parentCategory: Category): Subcategory
}