package org.soneira.savings.infrastructure.filereader.adapter

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.port.output.filereader.FileReader
import org.soneira.savings.domain.vo.FileParserSettings
import org.soneira.savings.domain.vo.FileReaderType
import org.soneira.savings.infrastructure.filereader.ExcelFileReader
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class FileReaderImpl: FileReader {
    override fun read(filename: String,
                      file: InputStream,
                      fileParserSettings: FileParserSettings): List<Movement> {
        return getReader(fileParserSettings).read(filename, file, fileParserSettings)
    }

    fun getReader(fileParserSettings: FileParserSettings): FileReader {
        when(fileParserSettings.fileType) {
            FileReaderType.EXCEL -> return ExcelFileReader()
        }
    }
}