package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.SubcategoryDocument
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SubcategoryMongoRepository : MongoRepository<SubcategoryDocument, String> {
    @Cacheable("subcategories")
    override fun findAll(): List<SubcategoryDocument>
}