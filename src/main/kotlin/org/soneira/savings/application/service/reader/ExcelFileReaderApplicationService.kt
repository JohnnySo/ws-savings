package org.soneira.savings.application.service.reader

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.soneira.savings.domain.entity.PreMovement
import org.soneira.savings.domain.exception.FileParseException
import org.soneira.savings.domain.port.input.FileReaderApplicationService
import org.soneira.savings.domain.vo.Field
import org.soneira.savings.domain.vo.FileParserSettings
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import java.io.InputStream
import java.math.BigDecimal

class ExcelFileReaderApplicationService : FileReaderApplicationService {
    override fun read(file: InputStream, fileParserSettings: FileParserSettings): List<PreMovement> {
        val workbook = HSSFWorkbook(file)
        val preMovements = ArrayList<PreMovement>()
        workbook.use {
            val sheet = workbook.getSheetAt(0)
            val rowIt = sheet.rowIterator()
            (0..fileParserSettings.headerOffset).forEach { _ -> rowIt.next() }
            var order = sheet.lastRowNum - fileParserSettings.headerOffset - 1
            while (rowIt.hasNext()) {
                val row = rowIt.next()
                preMovements.add(buildMovement(row, order, fileParserSettings.fieldPosition))
                order--
            }
        }
        return preMovements
    }

    private fun buildMovement(row: Row, order: Int, fieldPositions: Map<Field, Int>): PreMovement {
        val operationDate =
            fieldPositions[Field.OPERATION_DATE]?.let { row.getCell(it).localDateTimeCellValue.toLocalDate() }
        val category = fieldPositions[Field.CATEGORY]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val subCategory = fieldPositions[Field.SUBCATEGORY]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val description = fieldPositions[Field.DESCRIPTION]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val comment = fieldPositions[Field.COMMENT]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val amount = fieldPositions[Field.AMOUNT]?.let { BigDecimal.valueOf(row.getCell(it).numericCellValue) }
        val balance = fieldPositions[Field.BALANCE]?.let { BigDecimal.valueOf(row.getCell(it).numericCellValue) }
        if (operationDate == null || description == null || amount == null) {
            throw FileParseException(
                "The file format it is not valid. " +
                        "Please review the file format and the user defined format in the app."
            )
        } else {
            var moneyBalance = Money.ZERO
            if (balance != null) {
                moneyBalance = Money.of(balance)
            }
            return PreMovement(
                operationDate, description, Money.of(amount), Order(order),
                category, subCategory, comment, moneyBalance
            )
        }
    }
}