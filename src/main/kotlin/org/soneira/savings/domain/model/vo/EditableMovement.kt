package org.soneira.savings.domain.model.vo

import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.vo.id.MovementId

data class EditableMovement(
    val id: MovementId,
    val description: String,
    val subcategory: Subcategory,
    var comment: String,
    val order: Order,
)