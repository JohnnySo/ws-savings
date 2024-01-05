package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.mongodb.core.mapping.Document

@Document("subcategories")
data class SubcategoryDocument(
    val id: String,
    val keySubcategory: String,
    val descriptionSubcategory: String,
    val parentCategory: String
)