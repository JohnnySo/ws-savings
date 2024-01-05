package org.soneira.savings.domain.port.output.repository

import org.soneira.savings.domain.entity.User

interface UserRepository {
    /**
     * Get a user by his username.
     * This is temporal. The user should be authenticated and recover from spring session
     * @param email the email of the user
     * @return the full information of the user [User]
     */
    fun getUser(email: String): User
}