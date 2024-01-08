package org.soneira.savings.infrastructure.persistence.mongo.repository

import org.soneira.savings.infrastructure.persistence.mongo.document.FileDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FileMongoRepository : MongoRepository<FileDocument, String> {
    fun findByIdAndUserId(id: String, userId: String): Optional<FileDocument>
}