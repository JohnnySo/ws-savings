package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.CategoryMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.CategoryMongoRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class CategoryRepositoryImpl(
    val categoryMongoRepository: CategoryMongoRepository,
    val categoryMapper: CategoryMapper
) : CategoryRepository {
    @Cacheable("categories")
    override fun getAll(): List<Category> {
        val categories = categoryMongoRepository.findAll()
        return categories.map { categoryMapper.toDomain(it) }
    }
}