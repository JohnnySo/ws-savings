package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.model.vo.id.CategoryId
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.CategoryMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.CategoryMongoRepository
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryImpl(
    val categoryMongodbRepository: CategoryMongoRepository,
    val categoryMapper: CategoryMapper
) : CategoryRepository {
    override fun getAll(): List<Category> {
        val categories = categoryMongodbRepository.findAll()
        return categories.map { categoryMapper.asCategory(it) }
    }

    override fun getById(id: CategoryId) = getAll().first { it.id.value == id.value }
}