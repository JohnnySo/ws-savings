package org.soneira.savings.domain.usecase

import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.vo.id.FileId

interface GetFilesUseCase {
    /**
     * Get all the files for the user registered
     * @return List of [File]
     */
    fun getFiles(): List<File>

    /**
     * Get a file detail from its identifier
     * @return [File]
     */
    fun getFile(id: FileId): File
}