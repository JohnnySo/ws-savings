package org.soneira.savings.application.service.reader

import org.soneira.savings.domain.port.input.FileReaderApplicationService
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.FileReaderType
import org.springframework.stereotype.Component

@Component
class ReaderFactory(val subcategoryRepository: SubcategoryRepository) {
    /**
     * Returns a reader based on the configuration stored for this user
     * @param fileType the type of reader to instance
     * @return an instance of the required reader of type [FileReaderApplicationService]
     */
    fun getReader(fileType: FileReaderType): FileReaderApplicationService {
        when (fileType) {
            FileReaderType.EXCEL -> return ExcelFileReaderApplicationService(subcategoryRepository)
        }
    }
}