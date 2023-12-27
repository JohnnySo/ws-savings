package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.vo.id.FileId

interface FileRepository {
    /**
     * Save the info extracted from de file into database.
     * @param user the owner
     * @param filename the name of the file from which the info comes from
     * @param movements the list of movements extracted from the file
     * @return an object [File] with a file identifier and all the movements extracted
     */
    fun save(user: User, filename: String, movements: List<Movement>): File

    /**
     * Get a [File] from its identifier.
     * @param fileId the file identifier
     * @return an object [File] with a file identifier and all the movements
     */
    fun get(fileId: String): File

    /**
     * Delete a File from database.
     * @param file to remove
     */
    fun remove(file: File)
}