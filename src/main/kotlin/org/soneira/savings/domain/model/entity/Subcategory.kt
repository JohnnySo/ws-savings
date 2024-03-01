package org.soneira.savings.domain.model.entity

import org.soneira.savings.domain.model.vo.id.SubcategoryId
import java.math.BigInteger

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
        val DEFAULT_SUBCATEGORY = BigInteger("210")
    }
}