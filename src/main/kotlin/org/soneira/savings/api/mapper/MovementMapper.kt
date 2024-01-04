package org.soneira.savings.api.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.soneira.savings.api.dto.CategoryDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.dto.SubcategoryDTO
import org.soneira.savings.domain.entity.Category
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.PreMovement
import org.soneira.savings.domain.entity.Subcategory

@Mapper
interface MovementMapper {
    @Mapping(source = "id.value", target = "movementId")
    @Mapping(source = "amount.amount", target = "amount")
    @Mapping(source = "balance.amount", target = "balance")
    @Mapping(source = "order.value", target = "order")
    @Mapping(source = "subcategory.parentCategory", target = "category")
    fun toDto(movement: Movement): MovementDTO

    @Mapping(source = "id.value", target = "categoryId")
    @Mapping(source = "description", target = "categoryName")
    fun toDto(category: Category): CategoryDTO

    @Mapping(source = "subcategoryId.value", target = "subcategoryId")
    @Mapping(source = "descriptionSubcategory", target = "subcategoryName")
    fun toDto(subcategory: Subcategory): SubcategoryDTO

    @Mapping(source = "id.value", target = "movementId")
    @Mapping(source = "amount.amount", target = "amount")
    @Mapping(source = "balance.amount", target = "balance")
    @Mapping(source = "order.value", target = "order")
    @Mapping(expression = "java(new CategoryDTO(movement.getCategory(), movement.getCategory()))",
        target = "category")
    @Mapping(expression = "java(new SubcategoryDTO(movement.getSubcategory(), movement.getSubcategory()))",
        target = "subcategory")
    fun toDto(movement: PreMovement): MovementDTO
}