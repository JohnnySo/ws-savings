package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.Subcategory
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.SubcategoryMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.SubcategoryMongoRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class SubcategoryRepositoryImpl(
    val subcategoryMongoRepository: SubcategoryMongoRepository,
    val subCategoryMapper: SubcategoryMapper
) : SubcategoryRepository {

    @Cacheable("subcategories")
    override fun getAll(): List<Subcategory> {
        val subcategories = subcategoryMongoRepository.findAll()
        return subcategories.map { subCategoryMapper.toDomain(it) }
    }
}