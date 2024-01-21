package org.soneira.savings.domain.vo

import org.soneira.savings.domain.entity.Subcategory
import org.soneira.savings.domain.vo.id.MovementId

data class EditableMovement(
    val description: String,
    val subcategory: Subcategory,
    var comment: String,
    val order: Order,
) {
    var id: MovementId = MovementId("")
}