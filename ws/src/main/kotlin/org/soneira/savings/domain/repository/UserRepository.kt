package org.soneira.savings.domain.repository

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.vo.id.UserId

interface UserRepository {
    /**
     * Get a user by his username.
     * This is temporal. The user should be authenticated and recover from spring session
     * @param email the email of the user
     * @return the full information of the user [User]
     */
    fun getUser(email: String): User

    /**
     * Get a user by his identifier.
     * @param id the identifier of the user [UserId]
     * @return the full information of the user [User]
     */
    fun getUserById(id: UserId): User
}