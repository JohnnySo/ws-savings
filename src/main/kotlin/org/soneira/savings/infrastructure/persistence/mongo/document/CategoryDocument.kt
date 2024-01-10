package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("categories")
data class CategoryDocument(
    @Id val id: String,
    val key: String,
    val description: String,
    val typeId: Int,
    val type: String
)