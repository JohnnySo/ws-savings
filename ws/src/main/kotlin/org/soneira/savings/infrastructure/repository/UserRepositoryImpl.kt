package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.exception.ResourceNotFoundException
import org.soneira.savings.domain.model.vo.id.UserId
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.UserMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.UserMongoRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(
    val userMongodbRepository: UserMongoRepository,
    val userMapper: UserMapper
) : UserRepository {
    override fun getUser(email: String): User {
        val userDocument = userMongodbRepository.findByEmail(email)
        return userMapper.toDomain(userDocument)
    }

    override fun getUserById(id: UserId): User {
        val userDocument = userMongodbRepository.findById(id.value)
        return userDocument.map { userMapper.toDomain(it) }.orElseThrow {
            ResourceNotFoundException("The userId $id was not found.")
        }
    }
}