package org.soneira.savings.infrastructure.persistence.mongo.document

import org.soneira.savings.domain.model.vo.FileReaderType
import org.soneira.savings.domain.model.vo.ImportField
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.INT32

data class FileParserSettingsDocument(
    val fileType: FileReaderType,
    @Field(targetType = INT32)
    val headerOffset: Int,
    val fieldPosition: Map<ImportField, Int>
)