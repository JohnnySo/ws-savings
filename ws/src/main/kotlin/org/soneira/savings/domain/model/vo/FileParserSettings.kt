package org.soneira.savings.domain.model.vo

data class FileParserSettings(
    val fileType: FileReaderType,
    val headerOffset: Int,
    val fieldPosition: Map<ImportField, Int>
)