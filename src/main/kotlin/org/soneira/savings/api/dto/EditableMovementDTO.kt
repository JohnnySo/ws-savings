package org.soneira.savings.api.dto

data class EditableMovementDTO(
    val id: String,
    val description: String,
    val subcategory: SubcategoryDTO,
    val comment: String,
    val order: Int,
)