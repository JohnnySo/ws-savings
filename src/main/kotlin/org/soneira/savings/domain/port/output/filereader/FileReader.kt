package org.soneira.savings.domain.port.output.filereader

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.vo.FileParserSettings
import java.io.InputStream

interface FileReader {
    /**
     * Read a file and convert the information into a list of [Movement].
     * @param filename the name of the file
     * @param file the input stream
     * @param fileParserSettings the configuration needed to parse the file
     * @return the list of movements extracted from the file [Movement]
     */
    fun read(filename: String, file: InputStream, fileParserSettings: FileParserSettings): List<Movement>
}