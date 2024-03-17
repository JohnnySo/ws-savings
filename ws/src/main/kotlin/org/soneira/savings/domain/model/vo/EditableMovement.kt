package org.soneira.savings.domain.model.vo

import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.vo.id.MovementId

data class EditableMovement(val id: MovementId) {
    var description: String? = null
    var subcategory: Subcategory? = null
    var comment: String? = null
}