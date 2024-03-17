package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MovementMongoRepository : MongoRepository<MovementDocument, String> {
    fun findByPeriodId(periodId: String): List<MovementDocument>

    fun findByUserAndId(user: String, id: String): Optional<MovementDocument>

    fun findByUserAndPeriodId(user: String, periodId: String): List<MovementDocument>
}