package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("subcategories")
data class SubcategoryDocument(
    @Id val id: Int,
    val key: String,
    val description: String,
    val descriptionEs: String,
    val category: Int
)