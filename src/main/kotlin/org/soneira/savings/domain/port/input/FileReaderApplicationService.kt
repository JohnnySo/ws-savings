package org.soneira.savings.domain.port.input

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.vo.FileParserSettings
import java.io.InputStream

interface FileReaderApplicationService {
    /**
     * Read a file and convert the information into a list of [Movement].
     * @param file the input stream
     * @param fileParserSettings the configuration needed to parse the file
     * @return the list of movements extracted from the file [Movement]
     */
    fun read(file: InputStream, fileParserSettings: FileParserSettings): List<Movement>
}