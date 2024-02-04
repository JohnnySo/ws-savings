package org.soneira.savings.domain.port.input

import org.soneira.savings.domain.entity.File

interface RemoveFileApplicationService {
    /**
     * Delete a file from database
     * @param file to delete
     */
    fun remove(file: File)
}