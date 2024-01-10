package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("files")
data class FileDocument(
    val userId: String,
    val filename: String,
    val movements: List<PreMovementDocument>
) {
    @Id
    lateinit var id: String

    constructor(
        id: String, userId: String, filename: String, movements: List<PreMovementDocument>
    ) : this(userId, filename, movements) {
        this.id = id
    }
}