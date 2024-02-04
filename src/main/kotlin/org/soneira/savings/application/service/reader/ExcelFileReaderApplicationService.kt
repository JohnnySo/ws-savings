package org.soneira.savings.application.service.reader

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.exception.FileParseException
import org.soneira.savings.domain.port.input.FileReaderApplicationService
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.Field
import org.soneira.savings.domain.vo.FileParserSettings
import org.soneira.savings.domain.vo.Money
import org.soneira.savings.domain.vo.Order
import java.io.InputStream
import java.math.BigDecimal
import java.time.LocalDate

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
                movements.add(getMovementInfo(row, order, fileParserSettings.fieldPosition))
                order--
            }
        }
        return movements
    }

    private fun getMovementInfo(row: Row, order: Int, fieldPositions: Map<Field, Int>): Movement {
        val operationDate =
            fieldPositions[Field.OPERATION_DATE]?.let { row.getCell(it).localDateTimeCellValue.toLocalDate() }
        val subcategoryDescription =
            fieldPositions[Field.SUBCATEGORY]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val description = fieldPositions[Field.DESCRIPTION]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val comment = fieldPositions[Field.COMMENT]?.let { DataFormatter().formatCellValue(row.getCell(it)) } ?: ""
        val amount = fieldPositions[Field.AMOUNT]?.let {
            if (row.getCell(it).cellType == CellType.NUMERIC) {
                Money.of(BigDecimal.valueOf(row.getCell(it).numericCellValue))
            } else null
        }

        val balance = fieldPositions[Field.BALANCE]?.let {
            if (row.getCell(it).cellType == CellType.NUMERIC) {
                Money.of(BigDecimal.valueOf(row.getCell(it).numericCellValue))
            } else null
        } ?: Money.ZERO

        if (operationDate == null || description == null || amount == null) {
            throw FileParseException(
                "The file format is not valid. " +
                        "Please review the file format and the user-defined format in the app."
            )
        }
        return buildMovement(order, operationDate, subcategoryDescription, description, comment, amount, balance)
    }

    private fun buildMovement(
        order: Int,
        operationDate: LocalDate,
        subcategoryDescription: String?,
        description: String,
        comment: String,
        amount: Money,
        balance: Money
    ): Movement {
        return Movement(
            operationDate, description, amount, Order(order),
            subcategoryRepository.getByDescEsOrDefault(subcategoryDescription), comment, balance
        )
    }
}