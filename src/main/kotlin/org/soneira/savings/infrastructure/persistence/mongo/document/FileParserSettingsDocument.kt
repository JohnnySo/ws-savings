package org.soneira.savings.infrastructure.persistence.mongo.document

import org.soneira.savings.domain.vo.Field
import org.soneira.savings.domain.vo.FileReaderType

data class FileParserSettingsDocument(
    val fileType: FileReaderType,
    val headerOffset: Int,
    val fieldPosition: Map<Field, Int>
)