package org.soneira.savings.api.dto

import java.math.BigInteger

data class MovementDTO(val id: String) {
    var description: String? = null
    var subcategory: BigInteger? = null
    var comment: String? = null
}