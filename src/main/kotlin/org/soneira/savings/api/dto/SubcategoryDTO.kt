package org.soneira.savings.api.dto

data class SubcategoryDTO(val id: Int) {
    lateinit var description: String

    constructor(id: Int, description: String) :
            this(id) {
        this.description = description
    }
}