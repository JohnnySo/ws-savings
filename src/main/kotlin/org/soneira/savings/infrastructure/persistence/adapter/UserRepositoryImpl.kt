package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.exception.ResourceNotFoundException
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.UserMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.UserMongoRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class UserRepositoryImpl(
    val userMongoRepository: UserMongoRepository,
    val userMapper: UserMapper
) : UserRepository {
    override fun getUser(email: String): User {
        val userDocument = userMongoRepository.findByEmail(email)
        return userMapper.toDomain(userDocument)
    }

    override fun getUserById(id: String): User {
        val userDocument = userMongoRepository.findById(id)
        return userDocument.map { userMapper.toDomain(it) }.orElseThrow {
            ResourceNotFoundException("The userId $id was not found.")
        }
    }
}