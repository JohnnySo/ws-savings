package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.repository.MasterRepository
import org.soneira.savings.infrastructure.persistence.mongo.repository.CustomMongoRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class MasterRepositoryImpl(val customMongodbRepository: CustomMongoRepository) : MasterRepository {
    @Cacheable("years")
    override fun getYears(user: User): List<Int> {
        return customMongodbRepository.findDistinctYears(user.id.value)
    }

}