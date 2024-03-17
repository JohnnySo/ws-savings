package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.SubcategoryMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.SubcategoryMongoRepository
import org.springframework.stereotype.Component

@Component
class SubcategoryRepositoryImpl(
    val subcategoryMongoRepository: SubcategoryMongoRepository,
    val subCategoryMapper: SubcategoryMapper
) : SubcategoryRepository {


    override fun getAll(): List<Subcategory> {
        val subcategories = subcategoryMongoRepository.findAll()
        return subcategories.map { subCategoryMapper.asSubcategory(it) }
    }

    override fun getById(id: Int) = getAll().first { it.id.value == id }
}