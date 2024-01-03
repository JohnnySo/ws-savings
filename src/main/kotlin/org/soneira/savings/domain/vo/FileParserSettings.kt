package org.soneira.savings.domain.vo

data class FileParserSettings(
    val fileType: FileReaderType,
    val headerOffset: Int,
)