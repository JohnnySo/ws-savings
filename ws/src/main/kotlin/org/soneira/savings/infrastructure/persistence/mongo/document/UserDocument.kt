package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserDocument(
    @Id val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val settings: SettingsDocument
)