package org.soneira.savings.infrastructure.filereader

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.port.output.filereader.FileReader
import org.soneira.savings.domain.vo.FileParserSettings
import java.io.FileInputStream
import java.io.InputStream

class ExcelFileReader: FileReader {
    override fun read(filename: String, file: InputStream, fileParserSettings: FileParserSettings): List<Movement> {
        val workbook = HSSFWorkbook(FileInputStream(filename))
        workbook.use {  }
        TODO("Not yet implemented")
    }
}