package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.id.CategoryId

class Category(
    val id: CategoryId,
    val key: String,
    val description: String,
    val typeId: Int,
    val type: String
)