package org.soneira.savings.domain.model.entity

import org.soneira.savings.domain.model.vo.id.SubcategoryId

data class Subcategory(val id: SubcategoryId) {
    lateinit var key: String
    lateinit var description: String
    lateinit var descriptionEs: String
    lateinit var category: Category

    constructor(
        id: SubcategoryId, key: String, description: String, descriptionEs: String, category: Category
    ) : this(id) {
        this.key = key
        this.description = description
        this.descriptionEs = descriptionEs
        this.category = category
    }

    companion object {
        const val DEFAULT_SUBCATEGORY: Int = 210
    }
}