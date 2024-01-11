package org.soneira.savings.application.service.reader

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Subcategory
import org.soneira.savings.domain.exception.FileParseException
import org.soneira.savings.domain.port.input.FileReaderApplicationService
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.Field
import org.soneira.savings.domain.vo.FileParserSettings
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import java.io.InputStream
import java.math.BigDecimal

class ExcelFileReaderApplicationService(private val subcategoryRepository: SubcategoryRepository) :
    FileReaderApplicationService {
    override fun read(file: InputStream, fileParserSettings: FileParserSettings): List<Movement> {
        val workbook = HSSFWorkbook(file)
        val movements = ArrayList<Movement>()
        workbook.use {
            val sheet = workbook.getSheetAt(0)
            val rowIt = sheet.rowIterator()
            (0..fileParserSettings.headerOffset).forEach { _ -> rowIt.next() }
            var order = sheet.lastRowNum - fileParserSettings.headerOffset - 1
            while (rowIt.hasNext()) {
                val row = rowIt.next()
                movements.add(buildMovement(row, order, fileParserSettings.fieldPosition))
                order--
            }
        }
        return movements
    }

    private fun buildMovement(row: Row, order: Int, fieldPositions: Map<Field, Int>): Movement {
        val operationDate =
            fieldPositions[Field.OPERATION_DATE]?.let { row.getCell(it).localDateTimeCellValue.toLocalDate() }
        val subcategoryName =
            fieldPositions[Field.SUBCATEGORY]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val description = fieldPositions[Field.DESCRIPTION]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val comment = fieldPositions[Field.COMMENT]?.let { DataFormatter().formatCellValue(row.getCell(it)) } ?: ""
        val amount =
            fieldPositions[Field.AMOUNT]?.let { Money.of(BigDecimal.valueOf(row.getCell(it).numericCellValue)) }
        val balance =
            fieldPositions[Field.BALANCE]?.let { Money.of(BigDecimal.valueOf(row.getCell(it).numericCellValue)) }
                ?: Money.ZERO
        if (operationDate == null || description == null || amount == null) {
            throw FileParseException(
                "The file format it is not valid. " +
                        "Please review the file format and the user defined format in the app."
            )
        } else {
            val subcategories = subcategoryRepository.getAll()
            val subcategory = if (subcategoryName == null) {
                subcategories.first { Subcategory.DEFAULT_SUBCATEGORY == it.id.value }
            } else {
                subcategories.first { subcategoryName == it.descriptionEs }
            }
            return Movement(operationDate, description, amount, Order(order), subcategory, comment, balance)
        }
    }
}