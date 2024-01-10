package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("subcategories")
data class SubcategoryDocument(
    @Id val id: String,
    val keySubcategory: String,
    val descriptionSubcategory: String,
    val parentCategory: String
)