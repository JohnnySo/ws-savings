package org.soneira.savings.config

import org.jetbrains.annotations.NotNull
import org.soneira.savings.domain.service.period.PeriodCreator
import org.soneira.savings.domain.service.period.PeriodCreatorImpl
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EntityScan(basePackages = ["org.soneira.homepal.economicPeriods.persistence.mongo"])
@EnableMongoRepositories(basePackages = ["org.soneira.homepal.economicPeriods.persistence.mongo"])
@EnableAsync
@Configuration
class SavingsConfiguration {
    @Bean
    fun periodCreator(): PeriodCreator {
        return PeriodCreatorImpl()
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(@NotNull registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST")
            }
        }
    }
}