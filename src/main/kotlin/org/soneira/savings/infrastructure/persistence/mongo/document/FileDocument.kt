package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("files")
data class FileDocument(
    val user: String,
    val filename: String,
    val movements: List<MovementDocument>
) {
    @Id
    lateinit var id: String

    constructor(
        id: String, userId: String, filename: String, movements: List<MovementDocument>
    ) : this(userId, filename, movements) {
        this.id = id
    }
}