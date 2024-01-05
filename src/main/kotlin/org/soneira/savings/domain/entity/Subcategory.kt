package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.id.SubcategoryId

data class Subcategory(
    val id: SubcategoryId,
    val keySubcategory: String,
    val descriptionSubcategory: String,
    val parentCategory: Category
)