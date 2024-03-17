package org.soneira.savings.domain.model.entity

import org.soneira.savings.domain.model.vo.id.CategoryId

class Category(
    val id: CategoryId,
    val key: String,
    val description: String,
    val descriptionEs: String,
    val typeId: Int,
    val type: String
)