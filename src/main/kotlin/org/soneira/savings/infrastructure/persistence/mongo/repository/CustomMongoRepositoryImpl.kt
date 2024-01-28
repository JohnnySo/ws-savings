package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomMongoRepositoryImpl(val mongodbTemplate: MongoTemplate) {
    fun searchMovements(user: String, searchParam: String): List<MovementDocument> {
        val criteria = Criteria.where("user").`is`(user)
            .orOperator(
                Criteria.where("description").regex(".*$searchParam.*", "i"),
                Criteria.where("comment").regex(".*$searchParam.*", "i")
            )
        val query = Query.query(criteria).with(Sort.by(Sort.Order.by("description")))
        return mongodbTemplate.find(query, MovementDocument::class.java)
    }
}