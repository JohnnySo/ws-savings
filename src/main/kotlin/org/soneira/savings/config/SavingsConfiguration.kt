package org.soneira.savings.config

import org.soneira.savings.domain.service.period.PeriodCreator
import org.soneira.savings.domain.service.period.PeriodCreatorImpl
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableAsync


@EntityScan(basePackages = ["org.soneira.savings.infrastructure.persistence.mongo"])
@EnableMongoRepositories(basePackages = ["org.soneira.savings.infrastructure.persistence.mongo"])
@EnableAsync
@EnableCaching
@Configuration
class SavingsConfiguration {
    @Bean
    fun periodCreator(): PeriodCreator {
        return PeriodCreatorImpl()
    }

    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
        return MongoTransactionManager(dbFactory)
    }
}