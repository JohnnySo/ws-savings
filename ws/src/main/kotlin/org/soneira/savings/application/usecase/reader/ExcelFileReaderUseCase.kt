package org.soneira.savings.application.usecase.reader

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.Subcategory
import org.soneira.savings.domain.model.exception.FileParseException
import org.soneira.savings.domain.model.vo.FileParserSettings
import org.soneira.savings.domain.model.vo.ImportField
import org.soneira.savings.domain.model.vo.Money
import org.soneira.savings.domain.model.vo.Order
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.domain.usecase.FileReaderUseCase
import java.io.InputStream
import java.math.BigDecimal
import java.time.LocalDate

class ExcelFileReaderUseCase(private val subcategoryRepository: SubcategoryRepository) :
    FileReaderUseCase {
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

    private fun getMovementInfo(row: Row, order: Int, fieldPositions: Map<ImportField, Int>): Movement {
        val operationDate =
            fieldPositions[ImportField.OPERATION_DATE]?.let { row.getCell(it).localDateTimeCellValue.toLocalDate() }
        val subcategoryDescription =
            fieldPositions[ImportField.SUBCATEGORY]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val description =
            fieldPositions[ImportField.DESCRIPTION]?.let { DataFormatter().formatCellValue(row.getCell(it)) }
        val comment =
            fieldPositions[ImportField.COMMENT]?.let { DataFormatter().formatCellValue(row.getCell(it)) } ?: ""
        val amount = fieldPositions[ImportField.AMOUNT]?.let {
            if (row.getCell(it).cellType == CellType.NUMERIC) {
                Money.of(BigDecimal.valueOf(row.getCell(it).numericCellValue))
            } else null
        }

        val balance = fieldPositions[ImportField.BALANCE]?.let {
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
            getByDescEsOrDefault(subcategoryDescription), comment, balance
        )
    }

    /**
     * Get one subcategory by it's description, if not exist, return the default category
     * @param description description of subcategory
     * @return the subcategory that fits the identifier or the default one [Subcategory]
     */
    private fun getByDescEsOrDefault(description: String?): Subcategory {
        val subcategories = subcategoryRepository.getAll()
        var subcategory = subcategories.firstOrNull { it.descriptionEs == description }
        if (subcategory == null) {
            subcategory = subcategories.first { Subcategory.DEFAULT_SUBCATEGORY == it.id.value }
        }
        return subcategory
    }
}