package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.File

interface RemoveFileUseCase {
    /**
     * Delete a file from database
     * @param file to delete
     */
    fun remove(file: File)
}