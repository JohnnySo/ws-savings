package org.soneira.savings.api.dto


data class MovementDTO(val id: String) {
    var description: String? = null
    var subcategory: Int? = null
    var comment: String? = null
}