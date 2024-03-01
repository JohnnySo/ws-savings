package org.soneira.savings.application.usecase.reader

import org.soneira.savings.domain.model.vo.FileReaderType
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.domain.usecase.FileReaderUseCase
import org.springframework.stereotype.Component

@Component
class ReaderFactory(val subcategoryRepository: SubcategoryRepository) {
    /**
     * Returns a reader based on the configuration stored for this user
     * @param fileType the type of reader to instance
     * @return an instance of the required reader of type [FileReaderUseCase]
     */
    fun getReader(fileType: FileReaderType): FileReaderUseCase {
        when (fileType) {
            FileReaderType.EXCEL -> return ExcelFileReaderUseCase(subcategoryRepository)
        }
    }
}