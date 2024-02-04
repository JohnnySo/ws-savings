package org.soneira.savings.api.dto

import java.math.BigInteger

data class SubcategoryDTO(val id: BigInteger) {
    lateinit var description: String

    constructor(id: BigInteger, description: String) :
            this(id) {
        this.description = description
    }
}