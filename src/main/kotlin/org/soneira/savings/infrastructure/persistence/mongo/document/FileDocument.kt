package org.soneira.savings.infrastructure.persistence.mongo.document

data class FileDocument(
    val userId: String,
    val filename: String,
    val movements: List<MovementDocument>
) {
    var id: String = ""

    constructor(
        id: String, userId: String, filename: String, movements: List<MovementDocument>
    ) : this(userId, filename, movements) {
        this.id = id
    }
}