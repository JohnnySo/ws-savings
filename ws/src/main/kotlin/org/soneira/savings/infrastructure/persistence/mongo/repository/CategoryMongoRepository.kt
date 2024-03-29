package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.CategoryDocument
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface CategoryMongoRepository : MongoRepository<CategoryDocument, String> {
    @Cacheable("categories")
    override fun findAll(): List<CategoryDocument>
}