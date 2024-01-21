package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.EconomicPeriodDocument
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PeriodMongoRepository : MongoRepository<EconomicPeriodDocument, String> {

    @Query(value = "{ \"user\": :#{#user}}", sort = "{ \"year\": -1, \"month\": -1}")
    fun findLastPeriod(@Param("user") user: String): List<EconomicPeriodDocument>

    fun findByUserAndId(user: String, id: String): Optional<EconomicPeriodDocument>

    fun findAllByUser(user: String, pageable: Pageable): Page<EconomicPeriodDocument>
}