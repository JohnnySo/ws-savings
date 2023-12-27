package org.soneira.savings.domain.port.output.filereader

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import java.io.InputStream

interface Reader {
    /**
     * Read a file and convert the information into a list of [Movement].
     * @param filename the name of the file
     * @param file the input stream
     * @return the list of movements extracted from the file [Movement]
     */
    fun read(filename: String, file: InputStream): List<Movement>
}