package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.vo.FileParserSettings
import java.io.InputStream

interface FileReaderUseCase {
    /**
     * Read a file and convert the information into a list of [Movement].
     * @param file the input stream
     * @param fileParserSettings the configuration needed to parse the file
     * @return the list of movements extracted from the file [Movement]
     */
    fun read(file: InputStream, fileParserSettings: FileParserSettings): List<Movement>
}