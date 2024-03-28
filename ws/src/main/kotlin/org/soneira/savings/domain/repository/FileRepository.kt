package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.vo.id.FileId

interface FileRepository {
    /**
     * Save the info extracted from de file into database.
     * @param user the owner [User]
     * @param filename the name of the file from which the info comes from
     * @param movements the list of movements extracted from the file [Movement]
     * @return an object [File] with a file identifier and all the movements extracted
     */
    fun save(user: User, filename: String, movements: List<Movement>): File

    /**
     * Get a [File] from its identifier.
     * @param id the file identifier
     * @param user the user owner of the file [user]
     * @return an object [File] with a file identifier and all the movements
     */
    fun get(id: FileId, user: User): File

    /**
     * Get all the files of a user without movements.
     * @param user the user owner of the files [user]
     * @return a list of [File]
     */
    fun getAll(user: User): List<File>

    /**
     * Delete a File from database.
     * @param file to remove
     */
    fun remove(file: File)
}