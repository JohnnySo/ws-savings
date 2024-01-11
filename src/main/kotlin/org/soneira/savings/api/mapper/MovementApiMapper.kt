package org.soneira.savings.api.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.soneira.savings.api.dto.CategoryDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.dto.SubcategoryDTO
import org.soneira.savings.domain.entity.Category
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Subcategory

@Mapper
interface MovementApiMapper {
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "amount.amount", target = "amount")
    @Mapping(source = "balance.amount", target = "balance")
    @Mapping(source = "order.value", target = "order")
    @Mapping(source = "subcategory.category", target = "category")
    fun toDto(movement: Movement): MovementDTO

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "descriptionEs", target = "description")
    fun toDto(category: Category): CategoryDTO

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "descriptionEs", target = "description")
    fun toDto(subcategory: Subcategory): SubcategoryDTO
}