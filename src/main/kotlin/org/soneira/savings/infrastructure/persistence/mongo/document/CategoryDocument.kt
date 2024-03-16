package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.INT32
import org.springframework.data.mongodb.core.mapping.FieldType.INT64
import java.math.BigInteger

@Document("categories")
data class CategoryDocument(
    @Field(targetType = INT64)
    @Id val id: BigInteger,
    val key: String,
    val description: String,
    @Field("description_es")
    val descriptionEs: String,
    @Field("type_id", targetType = INT32)
    val typeId: Int,
    val type: String
)