package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.BaseEntity
import org.soneira.savings.domain.vo.id.CategoryId

open class Category(val key: String, val description: String, val typeId: Int, val type: String) :
    BaseEntity<CategoryId>()