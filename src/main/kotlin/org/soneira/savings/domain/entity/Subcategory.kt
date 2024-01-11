package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.id.SubcategoryId

data class Subcategory(
    val id: SubcategoryId,
    val key: String,
    val description: String,
    val descriptionEs: String,
    val category: Category
)