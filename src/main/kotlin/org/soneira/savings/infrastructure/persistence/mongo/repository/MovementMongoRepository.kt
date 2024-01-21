package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MovementMongoRepository : MongoRepository<MovementDocument, String> {
    fun findByPeriodId(periodId: String): List<MovementDocument>

    fun findByUserAndPeriodId(userId: String, periodId: String): List<MovementDocument>
}