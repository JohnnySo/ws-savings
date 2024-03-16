package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.Category
import org.soneira.savings.domain.repository.CategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.CategoryMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.CategoryMongoRepository
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class CategoryRepositoryImpl(
    val categoryMongoRepository: CategoryMongoRepository,
    val categoryMapper: CategoryMapper
) : CategoryRepository {
    override fun getAll(): List<Category> {
        val categories = categoryMongoRepository.findAll()
        return categories.map { categoryMapper.asCategory(it) }
    }

    override fun getById(id: BigInteger) = getAll().first { it.id.value == id }
}